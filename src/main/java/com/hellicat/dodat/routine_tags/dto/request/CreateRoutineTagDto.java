package com.hellicat.dodat.routine_tags.dto.request;

import java.util.List;

import com.hellicat.dodat.routine_detail.entity.RoutineDetailEntity;

public record CreateRoutineTagDto(
	String tag,
	List<RoutineDetailEntity> details) {

}
