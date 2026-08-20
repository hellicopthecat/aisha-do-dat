package com.hellicat.dodat.routines.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hellicat.dodat.routine_detail.dto.request.CreateRoutineDetailDto;
import com.hellicat.dodat.routines.entity.RoutineEntity;

@Service
public interface RoutineService {

	public RoutineEntity createRoutine(RoutineEntity routine, List<CreateRoutineDetailDto> detail);

}
