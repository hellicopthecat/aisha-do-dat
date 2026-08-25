package com.hellicat.dodat.routine_access.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hellicat.dodat.routine_access.dto.request.CreateAccessRoutineDto;
import com.hellicat.dodat.routine_access.entity.RoutineAccessEntity;

@Service
public interface RoutineAccessService {
	public RoutineAccessEntity createAccessRoutine(CreateAccessRoutineDto dto);

	public RoutineAccessEntity getAccessRoutine(UUID id);
}
