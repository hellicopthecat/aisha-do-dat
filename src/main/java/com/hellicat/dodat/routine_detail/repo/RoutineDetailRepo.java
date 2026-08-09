package com.hellicat.dodat.routine_detail.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hellicat.dodat.routine_detail.entity.RoutineDetailEntity;

public interface RoutineDetailRepo extends JpaRepository<RoutineDetailEntity, UUID> {
	RoutineDetailEntity getRoutineDetailById(UUID id);
}
