package com.hellicat.dodat.routines.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hellicat.dodat.routines.entity.RoutineEntity;

public interface RoutineRepo extends JpaRepository<RoutineEntity, UUID> {
	public List<RoutineEntity> findAllByUser_id(UUID userId);
}
