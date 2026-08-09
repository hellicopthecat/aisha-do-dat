package com.hellicat.dodat.routine_access.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hellicat.dodat.routine_access.entity.RoutineAccessEntity;

public interface RoutineAccessRepo extends JpaRepository<RoutineAccessEntity, UUID> {

}
