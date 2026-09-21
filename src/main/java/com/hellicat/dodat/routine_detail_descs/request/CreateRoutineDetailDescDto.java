package com.hellicat.dodat.routine_detail_descs.request;

import com.hellicat.dodat.routine_detail.entity.RoutineDetailEntity;

public record CreateRoutineDetailDescDto(
	String desc,
	RoutineDetailEntity RoutineDetail) {

}
