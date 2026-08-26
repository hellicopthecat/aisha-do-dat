package com.hellicat.dodat.routine_access.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hellicat.dodat.commons.enums.AccessEnum;
import com.hellicat.dodat.routine_access.dto.request.CreateAccessRoutineDto;
import com.hellicat.dodat.routine_access.entity.RoutineAccessEntity;
import com.hellicat.dodat.routine_access.repo.RoutineAccessRepo;
import com.hellicat.dodat.routines.entity.RoutineEntity;
import com.hellicat.dodat.routines.service.RoutineServiceImpl;
import com.hellicat.dodat.users.entity.UserEntity;
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
	public RoutineAccessEntity createAccessRoutine(CreateAccessRoutineDto dto) {

		RoutineEntity routine = r_service.getRoutine(dto.routine_id());

		if (routine.getUser().getId().equals(dto.owner_id())) {
			throw new IllegalArgumentException("루틴의 소유자가 아닙니다.");
		}

		UserEntity owner = u_service.findUserById(dto.owner_id());

		if (owner.getId().equals(dto.access_user_id())) {
			throw new IllegalArgumentException("본인을 대상으로 접근권한을 부여할 수 없습니다.");
		}

		UserEntity accessUser = u_service.findUserById(dto.access_user_id());

		RoutineAccessEntity routineAccess = RoutineAccessEntity.builder()
			.accessState(dto.status())
			.owner(owner)
			.access(accessUser)
			.routine(routine)
			.build();

		return repo.save(routineAccess);
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
