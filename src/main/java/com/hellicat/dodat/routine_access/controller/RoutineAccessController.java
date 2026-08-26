package com.hellicat.dodat.routine_access.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hellicat.dodat.global.dto.ResultDto;
import com.hellicat.dodat.routine_access.dto.request.CreateAccessRoutineDto;
import com.hellicat.dodat.routine_access.entity.RoutineAccessEntity;
import com.hellicat.dodat.routine_access.service.RoutineAccessServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/access-routine")
@RequiredArgsConstructor
public class RoutineAccessController {

	private final RoutineAccessServiceImpl r_accessService;

	@PostMapping
	public ResponseEntity<ResultDto<RoutineAccessEntity>> createAccessRoutine(
		@RequestBody
		CreateAccessRoutineDto dto) {

		RoutineAccessEntity accessRoutine = r_accessService.createAccessRoutine(dto);

		return ResponseEntity.status(HttpStatus.CREATED).body(ResultDto.success("접근 권한부여 성공", accessRoutine));
	}

	//	@GetMapping("/list")
	//	public ResponseEntity<ResultDto<>>

}
