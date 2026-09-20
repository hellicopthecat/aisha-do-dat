package com.hellicat.dodat.routine_access.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hellicat.dodat.global.dto.ResultDto;
import com.hellicat.dodat.routine_access.dto.request.CreateAccessRoutineDto;
import com.hellicat.dodat.routine_access.dto.response.AccessRoutineResponseDto;
import com.hellicat.dodat.routine_access.entity.RoutineAccessEntity;
import com.hellicat.dodat.routine_access.service.RoutineAccessServiceImpl;
import com.hellicat.dodat.routines.entity.RoutineEntity;
import com.hellicat.dodat.routines.service.RoutineServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/access-routine")
@RequiredArgsConstructor
public class RoutineAccessController {

	private final RoutineServiceImpl r_service;
	private final RoutineAccessServiceImpl r_accessService;

	@PostMapping
	public ResponseEntity<ResultDto<RoutineAccessEntity>> createAccessRoutine(
		@RequestBody
		CreateAccessRoutineDto dto) {

		RoutineAccessEntity accessRoutine = r_accessService.createAccessRoutine(dto);

		return ResponseEntity.status(HttpStatus.CREATED).body(ResultDto.success("접근 권한부여 성공", accessRoutine));
	}

	@GetMapping("/list")
	public ResponseEntity<ResultDto<List<AccessRoutineResponseDto>>> getAccessRoutine(
		@RequestParam(name = "routineId")
		UUID id) {
		List<AccessRoutineResponseDto> accessList = new ArrayList<AccessRoutineResponseDto>();
		RoutineEntity routine = r_service.findRoutineById(id);

		List<RoutineAccessEntity> routine_access = routine.getRoutine_access();

		if (routine_access.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResultDto.fail("루틴 접근권한 리스트 명단이 없습니다.", null));

		}

		for (RoutineAccessEntity access : routine_access) {
			accessList.add(AccessRoutineResponseDto.from(access));
		}
		return ResponseEntity.ok(ResultDto.success("루틴 접근권한 리스트 생성 성공", accessList));
	}

}
