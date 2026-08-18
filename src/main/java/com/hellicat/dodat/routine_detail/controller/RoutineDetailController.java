package com.hellicat.dodat.routine_detail.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hellicat.dodat.global.dto.ResultDto;
import com.hellicat.dodat.routine_detail.dto.request.UpdateRoutineDetailDto;
import com.hellicat.dodat.routine_detail.dto.response.RoutineDetailResponseDto;
import com.hellicat.dodat.routine_detail.entity.RoutineDetailEntity;
import com.hellicat.dodat.routine_detail.service.RoutineDetailServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/routine/detail")
@RequiredArgsConstructor
public class RoutineDetailController {

	private final RoutineDetailServiceImpl r_detailService;

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

	@GetMapping("/{id}")
	public ResponseEntity<ResultDto<RoutineDetailResponseDto>> getRoutineDetailById(@PathVariable("id")
	UUID id) {
		RoutineDetailEntity detail = r_detailService.getRoutineDetailById(id);
		return ResponseEntity.ok(ResultDto.success("디테일 루틴 불러오기 성공", RoutineDetailResponseDto.from(detail)));
	}

	@PatchMapping("/{id}/update")
	public ResponseEntity<ResultDto<Void>> updateRoutineDetail(@PathVariable("id")
	UUID id, @RequestBody
	UpdateRoutineDetailDto dto) {
		r_detailService.updateRoutineDetail(id, dto);
		return ResponseEntity.ok(ResultDto.success("루틴 수정 성공", null));
	}

}
