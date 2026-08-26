package com.hellicat.dodat.routine_access.dto.response;

import java.util.List;
import java.util.UUID;

import com.hellicat.dodat.routine_detail.entity.RoutineDetailEntity;

public record RoutineAccessListDto(
	UUID id,
	String tag,
	List<RoutineDetailEntity> routineDetails) {
	public final RoutineAccessListDto from() {

	}
}
