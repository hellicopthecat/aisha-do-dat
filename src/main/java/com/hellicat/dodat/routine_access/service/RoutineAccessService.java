package com.hellicat.dodat.routine_access.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hellicat.dodat.routine_access.entity.RoutineAccessEntity;

@Service
public interface RoutineAccessService {
	public RoutineAccessEntity createAccessRoutine(RoutineAccessEntity access);

	public RoutineAccessEntity getAccessRoutine(UUID id);
}
