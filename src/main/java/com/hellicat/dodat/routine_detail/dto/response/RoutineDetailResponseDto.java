package com.hellicat.dodat.routine_detail.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.hellicat.dodat.routine_detail.entity.RoutineDetailEntity;
import com.hellicat.dodat.routine_tags.entity.RoutineTagEntity;

public record RoutineDetailResponseDto(
	UUID id,
	Long priorityTwoDepth,
	LocalDateTime pre_event_start_at,
	LocalDateTime pre_event_end_at,
	LocalDateTime start_at,
	LocalDateTime end_at,
	String routine_desc_txt,
	List<RoutineTagEntity> tags,
	UUID routine_id) {

	public static RoutineDetailResponseDto from(RoutineDetailEntity detail) {

		return new RoutineDetailResponseDto(
			detail.getId(),
			detail.getPriorityTwoDepth(),
			detail.getPre_event_start_at(),
			detail.getPre_event_end_at(),
			detail.getStart_at(),
			detail.getEnd_at(),
			detail.getRoutine_desc_txt(),
			detail.getTags(),
			detail.getRoutine().getId());

	}

}
