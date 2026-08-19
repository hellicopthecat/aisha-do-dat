package com.hellicat.dodat.routine_tags.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hellicat.dodat.routine_tags.entity.RoutineTagEntity;

public interface RoutineTagRepo extends JpaRepository<RoutineTagEntity, UUID> {

}
