package com.hellicat.dodat.routine_detail.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hellicat.dodat.routine_detail.dto.request.CreateRoutineDetailDto;
import com.hellicat.dodat.routine_detail.dto.request.UpdateRoutineDetailDto;
import com.hellicat.dodat.routine_detail.entity.RoutineDetailEntity;
import com.hellicat.dodat.routine_detail.repo.RoutineDetailRepo;
import com.hellicat.dodat.routine_tags.entity.RoutineTagEntity;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RoutineDetailServiceImpl implements RoutineDetailService {

	private final RoutineDetailRepo repo;

	public RoutineDetailServiceImpl(RoutineDetailRepo repo) {
		this.repo = repo;
	}

	@Override
	public List<RoutineDetailEntity> createRoutineDetailList(List<CreateRoutineDetailDto> details) {

		List<RoutineDetailEntity> newDetails = new ArrayList<RoutineDetailEntity>();
		List<RoutineTagEntity> newTags = new ArrayList<RoutineTagEntity>();
		for (CreateRoutineDetailDto detail : details) {
			RoutineDetailEntity routineDetailEntity = RoutineDetailEntity.builder()
				.priorityTwoDepth(detail.priorityTwoDepth())
				.pre_event_start_at(detail.pre_event_start_at())
				.pre_event_end_at(detail.pre_event_end_at())
				.start_at(detail.start_at())
				.end_at(detail.end_at())
				.routine_desc_txt(detail.routine_desc_txt())
				.build();

			for (String tag : detail.tags()) {
				RoutineTagEntity routineTagEntity = RoutineTagEntity.builder()
					.tag(tag)
					.detail(routineDetailEntity)
					.build();
				newTags.add(routineTagEntity);
			}

			routineDetailEntity.updateTags(newTags);

			RoutineDetailEntity save = repo.save(routineDetailEntity);
			newDetails.add(save);
		}

		return newDetails;
	}

	@Override
	public RoutineDetailEntity createRoutineDetail(CreateRoutineDetailDto detail) {

		RoutineDetailEntity newOne = RoutineDetailEntity.builder()
			.priorityTwoDepth(detail.priorityTwoDepth())
			.pre_event_start_at(detail.pre_event_start_at())
			.pre_event_end_at(detail.pre_event_end_at())
			.start_at(detail.start_at())
			.end_at(detail.end_at())
			.routine_desc_txt(detail.routine_desc_txt()).build();

		return repo.save(newOne);
	}

	@Override
	public RoutineDetailEntity getRoutineDetailById(UUID id) {
		return repo.getRoutineDetailById(id).orElseThrow(() -> new EntityNotFoundException("해당 상세루틴은 존재하지 않습니다."));
	}

	public List<RoutineDetailEntity> findRoutinesById(UUID routindId) {
		return repo.findRoutinesById(routindId);
	}

	@Transactional
	public void updateRoutineDetail(UUID id, UpdateRoutineDetailDto dto) {

		RoutineDetailEntity routineDetail = getRoutineDetailById(id);

		if (dto.start_at != null && dto.end_at != null && dto.start_at.isAfter(dto.end_at)
			|| dto.end_at.isBefore(dto.start_at)) {
			throw new IllegalArgumentException("시작시작 혹은 종료시간은 앞서거나 뒤쳐 질 수 없습니다.");
		}
		if (dto.pre_event_start_at != null && dto.pre_event_end_at != null
			&& dto.pre_event_start_at.isAfter(dto.pre_event_end_at)
			|| dto.pre_event_end_at.isBefore(dto.pre_event_start_at)) {
			throw new IllegalArgumentException("시작시작 혹은 종료시간은 앞서거나 뒤쳐 질 수 없습니다.");
		}

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
