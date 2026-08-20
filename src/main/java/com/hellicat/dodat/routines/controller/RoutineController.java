package com.hellicat.dodat.routines.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hellicat.dodat.global.dto.ResultDto;
import com.hellicat.dodat.routine_detail.dto.request.CreateRoutineDetailDto;
import com.hellicat.dodat.routines.dto.request.RoutineCreateDto;
import com.hellicat.dodat.routines.dto.request.RoutineUpdateDto;
import com.hellicat.dodat.routines.dto.response.MyRoutineDto;
import com.hellicat.dodat.routines.dto.response.RoutineDto;
import com.hellicat.dodat.routines.entity.RoutineEntity;
import com.hellicat.dodat.routines.service.RoutineServiceImpl;
import com.hellicat.dodat.security.JwtTokenProvider;
import com.hellicat.dodat.users.entity.UserEntity;
import com.hellicat.dodat.users.service.UserServiceImpl;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/routines")
@RequiredArgsConstructor
public class RoutineController {

	private final UserServiceImpl userService;
	private final RoutineServiceImpl routineService;
	private final JwtTokenProvider jwtTokenProvider;

	/**
	 * 루틴생성 
	 * 
	 * @param cookie
	 * @param dto
	 * @return
	 */
	@PostMapping
	public ResponseEntity<ResultDto<Void>> createRoutine(
		@CookieValue("access_token")
		Cookie cookie,
		@RequestBody
		RoutineCreateDto dto) {

		UUID userId = getUserID(cookie);
		UserEntity user = userService.findUserById(userId);

		RoutineEntity routine = RoutineEntity.builder()
			.title(dto.title())
			.desc(dto.desc())
			.category(dto.category())
			.user(user)
			.build();

		List<CreateRoutineDetailDto> detail = dto.detail();

		RoutineEntity result = routineService.createRoutine(routine, detail);
		if (result != null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ResultDto.fail("루틴 생성 실패", HttpStatus.INTERNAL_SERVER_ERROR.toString()));
		}
		return ResponseEntity.ok(ResultDto.success("루틴 생성 성공", null));
	}

	/**
	 * 내 루틴들 찾기
	 * 
	 * @param cookie
	 * @return
	 */
	@GetMapping()
	public ResponseEntity<ResultDto<List<MyRoutineDto>>> findAllByUserId(
		@CookieValue(name = "access_token", required = true)
		Cookie cookie) {

		UUID userId = getUserID(cookie);
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

	private UUID getUserID(Cookie cookie) {
		UUID userId = jwtTokenProvider.parseUserId(cookie.getValue(), false);
		return userId;
	}
}
