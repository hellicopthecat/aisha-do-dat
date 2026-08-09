package com.hellicat.dodat.routines.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hellicat.dodat.commons.enums.OneDepthEnums;
import com.hellicat.dodat.routine_detail.entity.RoutineDetailEntity;
import com.hellicat.dodat.routine_detail.service.RoutineDetailService;
import com.hellicat.dodat.routines.entity.RoutineEntity;
import com.hellicat.dodat.routines.repo.RoutineRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RoutineServiceImpl implements RoutineService {
	private final RoutineRepo repo;
	private final RoutineDetailService routineDetailService;

	public RoutineServiceImpl(RoutineRepo repo, RoutineDetailService routineDetailService) {
		this.repo = repo;
		this.routineDetailService = routineDetailService;
	}

	@Override
	public RoutineEntity createRoutine(List<RoutineDetailEntity> detail, RoutineEntity routine) {
		List<RoutineDetailEntity> routineDetails = routineDetailService.createRoutineDetailList(detail);
		routine.initRoutineDetail(routineDetails);
		return repo.save(routine);
	}

	public RoutineEntity getRoutine(UUID id) {
		return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 루틴을 찾을 수 없습니다."));
	}

	@Transactional
	public void updateRoutineTitle(UUID id, String title) {
		RoutineEntity routine = getRoutine(id);
		routine.updateRoutineTitle(title);
	}

	@Transactional
	public void updateRoutineDesc(UUID id, String desc) {
		RoutineEntity routine = getRoutine(id);
		routine.updateRoutineDesc(desc);
	}

	@Transactional
	public void updateRoutineCategory(UUID id, OneDepthEnums category) {
		RoutineEntity routine = getRoutine(id);
		routine.updateRoutineCategory(category);
	}

	@Transactional
	public void addRoutineDetail(UUID id, RoutineDetailEntity detail) {
		RoutineEntity routine = getRoutine(id);
		routine.addRoutineDetail(detail);
	}
}
