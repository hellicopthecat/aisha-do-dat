package com.hellicat.dodat.users.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hellicat.dodat.global.dto.ResultDto;
import com.hellicat.dodat.users.dto.response.UserResponseDto;
import com.hellicat.dodat.users.entity.UserEntity;
import com.hellicat.dodat.users.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserServiceImpl userService;

	@GetMapping("/find")
	public ResponseEntity<ResultDto<UserResponseDto>> findUserByEmail(
		@RequestParam()
		String email) {

		UserEntity user = userService.findUserByEmail(email);

		return ResponseEntity.ok(
			ResultDto.success(email, UserResponseDto.from(user)));
	}

}
