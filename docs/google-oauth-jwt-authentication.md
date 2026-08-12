# Google OAuth + JWT 인증 설계

## 현재 로그인 흐름

이 서비스는 Spring Security의 `oauth2Login()`을 사용하지 않고, Google OAuth 인증 과정을 직접 처리한다.

1. 클라이언트가 `GET /api/auth/google/login`을 요청한다.
2. 서버는 CSRF 방지용 `state` 값을 쿠키에 저장한 뒤 Google 로그인 페이지로 리다이렉트한다.
3. Google은 인가 코드(`code`)를 서버의 콜백 URL로 전달한다.
4. 서버는 Google 토큰 API로 `code`를 교환하고, 사용자 정보 API에서 이메일과 Google 고유 ID를 조회한다.
5. 이메일 기준으로 내부 사용자를 찾거나 새로 생성한다.
6. 내부 사용자 ID(`UserEntity.id`, `UUID`)를 JWT subject에 담아 access/refresh token을 발급한다.

따라서 Google 인증이 끝난 뒤 API 요청의 사용자 식별 기준은 Google 사용자 정보가 아니라 **우리 서비스의 사용자 ID(UUID)** 이다.

## `CustomUserDetail`이 필요한가?

현재 구조에서 단순히 로그인 사용자를 식별하거나 루틴/레시피의 소유자를 검사하는 용도라면 필요하지 않다.

`UserDetails`와 `CustomUserDetail`은 보통 Spring Security의 폼 로그인 또는 `UserDetailsService` 기반 인증에서 사용한다. 현재는 자체 JWT를 발급하므로, JWT 필터가 토큰의 subject를 파싱한 `UUID userId`를 `Authentication.principal`에 저장하면 된다.

`CustomUserDetail`은 다음처럼 이메일·이름·권한을 매 요청의 principal에서 자주 필요로 하거나, `UserDetailsService` 기반 인증을 도입할 때 검토한다.

## 필요한 구성

### 1. JWT 인증 필터

보호 API를 호출할 때 클라이언트는 access token을 아래 헤더로 보낸다.

```http
Authorization: Bearer {access-token}
```

필터는 다음을 수행한다.

1. `Authorization` 헤더에서 Bearer 토큰을 추출한다.
2. `JwtTokenProvider.parseUserId(token, false)`로 서명과 만료 시간을 검증하고 사용자 ID를 가져온다.
3. `UUID userId`를 principal로 하는 `Authentication`을 만든다.
4. `SecurityContextHolder`에 저장한다.

예시:

```java
UUID userId = jwtTokenProvider.parseUserId(accessToken, false);

Authentication authentication = new UsernamePasswordAuthenticationToken(
    userId,
    null,
    Collections.emptyList()
);

SecurityContextHolder.getContext().setAuthentication(authentication);
```

### 2. Security 설정

JWT 인증 필터를 `UsernamePasswordAuthenticationFilter` 이전에 등록한다. `/api/auth/google/**`과 같이 로그인 시작 및 콜백에 쓰는 경로는 인증 없이 접근할 수 있어야 한다. 나머지 보호 API는 인증을 요구한다.

```java
http
    .csrf(csrf -> csrf.disable())
    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    .authorizeHttpRequests(auth -> auth
        .requestMatchers("/api/auth/google/**").permitAll()
        .anyRequest().authenticated()
    )
    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
```

### 3. 소유자 검사

`Authentication`의 principal은 위 필터에서 저장한 `UUID`다. 따라서 서비스 계층에서 현재 사용자 ID와 리소스 작성자 ID를 비교한다.

```java
public boolean isOwner(UUID routineId, Authentication authentication) {
    if (authentication == null
        || !(authentication.getPrincipal() instanceof UUID userId)) {
        return false;
    }

    return routineRepository.existsByIdAndUserId(routineId, userId);
}
```

컨트롤러에서는 다음처럼 전달할 수 있다.

```java
@DeleteMapping("/{routineId}")
public ResponseEntity<Void> delete(
    @PathVariable UUID routineId,
    Authentication authentication
) {
    routineService.delete(routineId, authentication);
    return ResponseEntity.noContent().build();
}
```

가능하면 조회 후 Java에서 직접 비교하기보다 `existsByIdAndUserId(...)` 또는 `deleteByIdAndUserId(...)` 같은 Repository 쿼리로 처리한다. 이렇게 하면 다른 사용자의 리소스 존재 여부도 불필요하게 노출하지 않는다.

## 확인 및 수정할 점

현재 Google 로그인 시작 코드의 redirect URI와 콜백 컨트롤러 매핑이 서로 다르다.

| 위치 | 현재 값 |
| --- | --- |
| Google 요청 `redirect_uri` | `/api/auth/google/response` |
| `@GetMapping` 콜백 경로 | `/login/response` |

두 값을 동일하게 맞춰야 Google 콜백이 정상적으로 컨트롤러에 도착한다. 현재 컨트롤러의 전체 매핑을 기준으로 한다면 콜백 URI는 `/api/auth/google/login/response`가 되어야 한다. 이 URI는 Google Cloud Console의 승인된 리디렉션 URI에도 똑같이 등록해야 한다.
