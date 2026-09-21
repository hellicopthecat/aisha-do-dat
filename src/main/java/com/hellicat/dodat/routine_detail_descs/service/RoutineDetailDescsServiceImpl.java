package com.hellicat.dodat.routine_detail_descs.service;

import org.springframework.stereotype.Service;

import com.hellicat.dodat.routine_detail_descs.entity.RoutineDetailDescsEntity;
import com.hellicat.dodat.routine_detail_descs.repo.RoutineDetailDescsRepo;
import com.hellicat.dodat.routine_detail_descs.request.CreateRoutineDetailDescDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoutineDetailDescsServiceImpl implements RoutineDetailDescsService {

	private final RoutineDetailDescsRepo repo;

	@Override
	public RoutineDetailDescsEntity createRoutineDetailDesc(CreateRoutineDetailDescDto dto) {
		RoutineDetailDescsEntity desc = RoutineDetailDescsEntity.builder()
			.desc(dto.desc()).routine_detail(dto.RoutineDetail()).build();
		return repo.save(desc);
	}

	@Override
	public void findRoutineDetailDescs() {
		// TODO Auto-generated method stub

	}

	@Override
	public void findRoutineDetailDescById() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRoutineDetailDescById() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRoutineDetailDescById() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addRoutineDetailDesc() {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeRoutineDetailDesc() {
		// TODO Auto-generated method stub

	}

}
