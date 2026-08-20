package com.hellicat.dodat.routine_tags.service;

import com.hellicat.dodat.routine_detail.dto.request.CreateRoutineDetailDto;
import com.hellicat.dodat.routine_tags.entity.RoutineTagEntity;

public interface RoutineTagService {

	public RoutineTagEntity createRoutineTag(CreateRoutineDetailDto dto);
}
