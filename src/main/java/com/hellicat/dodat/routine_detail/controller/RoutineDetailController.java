package com.hellicat.dodat.routine_detail.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hellicat.dodat.auth.service.AuthService;
import com.hellicat.dodat.global.dto.ResultDto;
import com.hellicat.dodat.routine_detail.dto.request.CreateRoutineDetailDto;
import com.hellicat.dodat.routine_detail.dto.request.UpdateRoutineDetailDto;
import com.hellicat.dodat.routine_detail.dto.response.RoutineDetailResponseDto;
import com.hellicat.dodat.routine_detail.entity.RoutineDetailEntity;
import com.hellicat.dodat.routine_detail.service.RoutineDetailServiceImpl;
import com.hellicat.dodat.routines.entity.RoutineEntity;
import com.hellicat.dodat.routines.service.RoutineServiceImpl;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/routine/detail")
@RequiredArgsConstructor
public class RoutineDetailController {

	private final RoutineServiceImpl r_service;
	private final RoutineDetailServiceImpl r_detailService;
	private final AuthService authService;

	// 상세루틴 벌크 생성
	@Transactional
	@PostMapping
	public ResponseEntity<ResultDto<List<RoutineDetailResponseDto>>> createRoutineDetails(
		@RequestParam("routine")
		UUID routine_id,
		@CookieValue("access_token")
		Cookie cookie,
		@RequestBody
		List<CreateRoutineDetailDto> dtos) {

		UUID userID = authService.getUserID(cookie);

		RoutineEntity routine = r_service.findRoutineById(routine_id);

		boolean anyMatch = routine.getOwner().getId() == userID &&
			routine.getRoutine_access()
				.stream()
				.anyMatch(access -> access.getAccess_user().getId() == userID);

		if (!anyMatch) {
			return null;
		}

		List<RoutineDetailEntity> routineDetailList = r_detailService.createRoutineDetailList(routine, dtos);

		return null;
	}

	// 루틴의 상세루틴 배열 조회
	@GetMapping("/routines/{routineId}")
	public ResponseEntity<ResultDto<List<RoutineDetailResponseDto>>> findRoutinesById(@PathVariable("routineId")
	UUID id) {
		List<RoutineDetailEntity> routines = r_detailService.findRoutinesById(id);
		if (routines.isEmpty()) {
			return ResponseEntity.ok(ResultDto.success("조회된 세부리스트가 없습니다.", new ArrayList<>()));
		}
		List<RoutineDetailResponseDto> list = routines.stream().map(r -> RoutineDetailResponseDto.from(r)).toList();

		return ResponseEntity.ok(ResultDto.success(null, list));
	}

	// 해당 상세루틴 조회
	@GetMapping("/{id}")
	public ResponseEntity<ResultDto<RoutineDetailResponseDto>> getRoutineDetailById(@PathVariable("id")
	UUID id) {
		RoutineDetailEntity detail = r_detailService.getRoutineDetailById(id);
		return ResponseEntity.ok(ResultDto.success("디테일 루틴 불러오기 성공", RoutineDetailResponseDto.from(detail)));
	}

	// 해당 상세루틴 업데이트 
	@PatchMapping("/{id}/update")
	public ResponseEntity<ResultDto<Void>> updateRoutineDetail(@PathVariable("id")
	UUID id, @RequestBody
	UpdateRoutineDetailDto dto) {
		r_detailService.updateRoutineDetail(id, dto);
		return ResponseEntity.ok(ResultDto.success("루틴 수정 성공", null));
	}

}
