package com.hellicat.dodat.routine_access.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hellicat.dodat.commons.enums.AccessEnum;
import com.hellicat.dodat.routine_access.entity.RoutineAccessEntity;
import com.hellicat.dodat.routine_access.repo.RoutineAccessRepo;
import com.hellicat.dodat.routines.service.RoutineServiceImpl;
import com.hellicat.dodat.users.service.UserServiceImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoutineAccessServiceImpl implements RoutineAccessService {

	private final RoutineAccessRepo repo;
	private final RoutineServiceImpl r_service;
	private final UserServiceImpl u_service;

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
