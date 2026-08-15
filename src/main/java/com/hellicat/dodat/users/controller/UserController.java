package com.hellicat.dodat.users.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hellicat.dodat.global.dto.ResultDto;
import com.hellicat.dodat.security.JwtTokenProvider;
import com.hellicat.dodat.users.dto.response.FindUserResponseDto;
import com.hellicat.dodat.users.entity.UserEntity;
import com.hellicat.dodat.users.service.UserServiceImpl;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserServiceImpl userService;
	private final JwtTokenProvider jwtTokenProvider;

	@GetMapping("/find")
	public ResponseEntity<ResultDto<FindUserResponseDto>> findUserByEmail(
		@RequestParam()
		String email) {

		UserEntity user = userService.findUserByEmail(email);

		return ResponseEntity.ok(
			ResultDto.success(email, FindUserResponseDto.from(user)));
	}

	@GetMapping("/logout")
	public ResponseEntity<ResultDto<?>> logoutUser(@CookieValue(value = "access_token", required = true)
	Cookie cookie) {

		if (cookie != null) {
			UUID userId = jwtTokenProvider.parseUserId(cookie.getValue(), false);
			userService.logoutUser(userId);

			return ResponseEntity.ok(
				ResultDto.success("로그아웃 성공", null));
		}

		return ResponseEntity
			.status(HttpStatus.UNAUTHORIZED)
			.body(ResultDto.fail("로그아웃에 실패하였습니다.", HttpStatus.UNAUTHORIZED.toString()));
	}
}
