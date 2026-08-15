package com.hellicat.dodat.routines.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hellicat.dodat.routine_detail.entity.RoutineDetailEntity;
import com.hellicat.dodat.routines.entity.RoutineEntity;

@Service
public interface RoutineService {

	public RoutineEntity createRoutine(List<RoutineDetailEntity> detail, RoutineEntity routine);

}
