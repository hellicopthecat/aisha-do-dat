package com.hellicat.dodat.routines.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hellicat.dodat.routine_detail.dto.request.CreateRoutineDetailDto;
import com.hellicat.dodat.routine_detail.entity.RoutineDetailEntity;
import com.hellicat.dodat.routine_detail.service.RoutineDetailService;
import com.hellicat.dodat.routines.dto.request.RoutineUpdateDto;
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
	public RoutineEntity createRoutine(RoutineEntity routine, List<CreateRoutineDetailDto> detailList) {
		List<RoutineDetailEntity> routineDetails = routineDetailService.createRoutineDetailList(routine, detailList);
		routine.initRoutineDetail(routineDetails);
		return repo.save(routine);
	}

	public RoutineEntity findRoutineById(UUID id) {
		return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 루틴을 찾을 수 없습니다."));
	}

	@Override
	public List<RoutineEntity> findAllRoutinesByUserId(UUID userID) {
		return repo.findAllByUser_id(userID);
	}

	@Transactional
	public RoutineEntity updateRoutine(UUID id, RoutineUpdateDto dto) {
		RoutineEntity routine = findRoutineById(id);
		if (dto.title() != null) {
			routine.updateRoutineTitle(dto.title());
		}
		if (dto.desc() != null) {
			routine.updateRoutineDesc(dto.desc());
		}
		if (dto.priorityOneDepth() != null) {
			routine.updateRoutinePriorityOneDepth(dto.priorityOneDepth());
		}
		if (dto.category() != null) {
			routine.updateRoutineCategory(dto.category());
		}
		return routine;
	}

	@Transactional
	public void addRoutineDetail(UUID id, List<RoutineDetailEntity> details) {
		RoutineEntity routine = findRoutineById(id);
		for (RoutineDetailEntity detail : details) {
			routine.addRoutineDetail(detail);
		}
	}
}
