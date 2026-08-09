package com.hellicat.dodat.routine_detail.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hellicat.dodat.routine_detail.entity.RoutineDetailEntity;

@Service
public interface RoutineDetailService {

	List<RoutineDetailEntity> createRoutineDetailList(List<RoutineDetailEntity> details);

	RoutineDetailEntity createRoutineDetail(RoutineDetailEntity detail);

	RoutineDetailEntity getRoutineDetailById(UUID id);

}
