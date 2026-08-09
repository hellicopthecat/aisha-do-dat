package com.hellicat.dodat.routine_access.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hellicat.dodat.commons.enums.AccessEnum;
import com.hellicat.dodat.routine_access.entity.RoutineAccessEntity;
import com.hellicat.dodat.routine_access.repo.RoutineAccessRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RoutineAccessServiceImpl implements RoutineAccessService {

	private final RoutineAccessRepo repo;

	public RoutineAccessServiceImpl(RoutineAccessRepo repo) {
		this.repo = repo;
	}

	@Override
	public RoutineAccessEntity createAccessRoutine(RoutineAccessEntity access) {
		return repo.save(access);
	}

	@Override
	public RoutineAccessEntity getAccessRoutine(UUID id) {
		return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 루틴접근권한을 탐색할 수 없습니다."));
	}

	public void updateAccessStatus(UUID id, AccessEnum status) {
		RoutineAccessEntity accessRoutine = getAccessRoutine(id);
		accessRoutine.updateAccessStatus(status);
	}
}
