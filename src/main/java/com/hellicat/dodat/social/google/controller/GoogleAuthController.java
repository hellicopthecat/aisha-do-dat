package com.hellicat.dodat.social.google.controller;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import com.hellicat.dodat.commons.enums.SocialEnums;
import com.hellicat.dodat.security.JwtTokenProvider;
import com.hellicat.dodat.social.google.dto.GoogleTokenResponse;
import com.hellicat.dodat.social.google.dto.GoogleUserResponse;
import com.hellicat.dodat.users.entity.UserEntity;
import com.hellicat.dodat.users.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth/google")
public class GoogleAuthController {

	private final UserService userService;
	private final JwtTokenProvider jwtProvider;
	private final CsrfTokenRepository csrfTokenRepository;

	public GoogleAuthController(UserService userService, JwtTokenProvider provider,
		CsrfTokenRepository csrfTokenRepository) {
		this.userService = userService;
		this.jwtProvider = provider;
		this.csrfTokenRepository = csrfTokenRepository;
	}

	@GetMapping("/login")
	public void googleLogin(HttpServletResponse res) throws IOException {
		MultiValueMap<String, String> qp = new LinkedMultiValueMap<String, String>();

		String uuid = UUID.randomUUID().toString();

		ResponseCookie cookie = ResponseCookie.from("google_state", uuid)
			.httpOnly(true)
			.secure(true)
			.sameSite("Lax")
			.maxAge(600)
			.path("/api/auth/google")
			.build();

		res.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

		String googleClientId = System.getenv("GOOGLE_CLIENT_ID");

		qp.add("client_id", googleClientId);
		qp.add("redirect_uri", "http://localhost:8080/api/auth/google/response");
		qp.add("response_type", "code");
		qp.add("scope", "email openid profile");
		qp.add("state", uuid);

		UriComponentsBuilder uri = UriComponentsBuilder.fromUriString("https://accounts.google.com/o/oauth2/v2/auth")
			.queryParams(qp);

		res.sendRedirect(uri.toUriString());

	}

	@GetMapping("/login/response")
	public ResponseEntity<?> googleLoginResponse(
		@CookieValue("google_state")
		String googleState,
		@RequestParam("state")
		String state,
		@RequestParam("code")
		String code,
		HttpServletRequest req,
		HttpServletResponse res) {

		ResponseCookie cookie = ResponseCookie.from("google_state", "")
			.httpOnly(true)
			.secure(true)
			.sameSite("Lax")
			.maxAge(0)
			.path("/api/auth/google")
			.build();

		if (!googleState.equals(state)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(null);
		}

		if (code.equals("")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(null);
		}

		MultiValueMap<String, String> qp = new LinkedMultiValueMap<String, String>();

		String googleClientId = System.getenv("GOOGLE_CLIENT_ID");
		String googleScretKey = System.getenv("GOOGLE_SECRET_KEY");

		qp.add("client_id", googleClientId);
		qp.add("client_secret", googleScretKey);
		qp.add("code", code);
		qp.add("grant_type", "authorization_code");
		qp.add("redirect_uri", "http://localhost:8080/api/auth/google/response");

		RestClient restClient = RestClient.create();
		String refreshToken;
		String accessToken;
		try {
			GoogleTokenResponse tokenResponse = restClient.post().uri("https://oauth2.googleapis.com/token")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.body(qp)
				.retrieve()
				.body(GoogleTokenResponse.class);
			// 요청에서 정보가져오기
			GoogleUserResponse userInfo = restClient.get().uri("https://openidconnect.googleapis.com/v1/userinfo")
				.headers(headers -> headers.setBearerAuth(tokenResponse.accessToken()))
				.retrieve()
				.body(GoogleUserResponse.class);

			// 요청정보에서 회원찾기 

			String email = userInfo.email();
			String provider_id = userInfo.sub();
			String name = userInfo.name();

			UserEntity user = userService.findUserByEmail(email);
			if (user == null) {
				user = userService.createUser(
					UserEntity.builder()
						.email(email)
						.social(SocialEnums.GOOGLE)
						.providerId(provider_id)
						.name(name)
						.build());
			}

			// refresh token 발급
			refreshToken = jwtProvider.createRefreshToken(user.getId());
			user.updateRefreshToken(refreshToken);

			// access token 발급
			accessToken = jwtProvider.createAccessToken(user.getId());

			// 회원 가입 및 refresh token update
		} catch (RestClientResponseException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
		}

		CsrfToken csrfToken = csrfTokenRepository.generateToken(req);
		csrfTokenRepository.saveToken(csrfToken, req, res);

		return ResponseEntity.ok(Map.of("access_token", accessToken, "refresh_token", refreshToken));
	}

}
