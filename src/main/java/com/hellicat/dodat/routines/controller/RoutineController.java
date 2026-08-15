package com.hellicat.dodat.routines.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hellicat.dodat.global.dto.ResultDto;
import com.hellicat.dodat.routines.dto.request.RoutineUpdateDto;
import com.hellicat.dodat.routines.dto.response.MyRoutineDto;
import com.hellicat.dodat.routines.dto.response.RoutineDto;
import com.hellicat.dodat.routines.entity.RoutineEntity;
import com.hellicat.dodat.routines.service.RoutineServiceImpl;
import com.hellicat.dodat.security.JwtTokenProvider;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/routine")
@RequiredArgsConstructor
public class RoutineController {

	private final RoutineServiceImpl routineService;
	private final JwtTokenProvider jwtTokenProvider;

	@GetMapping("/my-routines")
	public ResponseEntity<ResultDto<List<MyRoutineDto>>> findAllByUserId(
		@CookieValue(name = "access_token", required = true)
		Cookie cookie) {

		UUID userId = jwtTokenProvider.parseUserId(cookie.getValue(), false);
		List<RoutineEntity> myRoutine = routineService.findAllByUserId(userId);

		return ResponseEntity.ok(ResultDto.success(null, MyRoutineDto.from(myRoutine)));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResultDto<RoutineDto>> getRoutine(
		@PathVariable("id")
		UUID uid) {
		RoutineEntity routine = routineService.getRoutine(uid);
		return ResponseEntity.ok(ResultDto.success(null, RoutineDto.from(routine)));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ResultDto<RoutineDto>> updateRoutine(
		@PathVariable("id")
		UUID uid,
		@RequestBody
		RoutineUpdateDto dto) {
		RoutineEntity routine = routineService.updateRoutine(uid, dto);
		return ResponseEntity.ok(ResultDto.success("루틴 수정 성공", RoutineDto.from(routine)));
	}

}
