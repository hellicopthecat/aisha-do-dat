package com.hellicat.dodat.routine_detail_descs.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hellicat.dodat.routine_detail_descs.entity.RoutineDetailDescsEntity;

public interface RoutineDetailDescsRepo extends JpaRepository<RoutineDetailDescsEntity, UUID> {

}
