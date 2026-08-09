package com.hellicat.dodat.routine_detail.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hellicat.dodat.routine_detail.dtos.UpdateRoutineDetailDto;
import com.hellicat.dodat.routine_detail.entity.RoutineDetailEntity;
import com.hellicat.dodat.routine_detail.repo.RoutineDetailRepo;

@Service
public class RoutineDetailServiceImpl implements RoutineDetailService {

	private final RoutineDetailRepo repo;

	public RoutineDetailServiceImpl(RoutineDetailRepo repo) {
		this.repo = repo;
	}

	@Override
	public List<RoutineDetailEntity> createRoutineDetailList(List<RoutineDetailEntity> details) {
		List<RoutineDetailEntity> newDetails = new ArrayList<RoutineDetailEntity>();
		for (RoutineDetailEntity detail : details) {
			RoutineDetailEntity save = repo.save(detail);
			newDetails.add(save);
		}
		return newDetails;
	}

	@Override
	public RoutineDetailEntity createRoutineDetail(RoutineDetailEntity detail) {
		return repo.save(detail);
	}

	@Override
	public RoutineDetailEntity getRoutineDetailById(UUID id) {
		return repo.getRoutineDetailById(id);
	}

	public void updateRoutineDetail(UUID id, UpdateRoutineDetailDto dto) {

		RoutineDetailEntity routineDetail = getRoutineDetailById(id);

		if (dto.pre_event_start_at != null) {
			routineDetail.updatePreEventStartAt(dto.pre_event_start_at);
		}

		if (dto.pre_event_end_at != null) {
			routineDetail.updatePreEventEndAt(dto.pre_event_end_at);
		}

		if (dto.start_at != null) {
			routineDetail.updateStartAt(dto.start_at);
		}

		if (dto.end_at != null) {
			routineDetail.updateEndAt(dto.end_at);
		}

		if (dto.routine_desc_txt != null) {
			routineDetail.updateDescTxt(dto.routine_desc_txt);
		}

	}
}
