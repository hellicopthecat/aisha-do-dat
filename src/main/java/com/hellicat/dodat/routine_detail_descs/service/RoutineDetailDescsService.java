package com.hellicat.dodat.routine_detail_descs.service;

import org.springframework.stereotype.Service;

import com.hellicat.dodat.routine_detail_descs.entity.RoutineDetailDescsEntity;
import com.hellicat.dodat.routine_detail_descs.request.CreateRoutineDetailDescDto;

@Service
public interface RoutineDetailDescsService {

	// create
	public RoutineDetailDescsEntity createRoutineDetailDesc(CreateRoutineDetailDescDto dto);

	// List find
	public void findRoutineDetailDescs();

	// find
	public void findRoutineDetailDescById();

	// update
	public void updateRoutineDetailDescById();

	// delete
	public void deleteRoutineDetailDescById();

	// put
	public void addRoutineDetailDesc();

	// patch
	public void removeRoutineDetailDesc();
}
