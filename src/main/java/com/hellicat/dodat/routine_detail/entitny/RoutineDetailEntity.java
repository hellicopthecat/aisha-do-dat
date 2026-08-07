package com.hellicat.dodat.routine_detail.entitny;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.hellicat.dodat.global.entity.BaseTimeEntity;
import com.hellicat.dodat.routines.entity.RoutineEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoutineDetailEntity extends BaseTimeEntity {

	@Id
	@UuidGenerator
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "routine_id")
	private RoutineEntity routine;

	@Column
	private LocalDateTime pre_event_at; // 루틴 예상 시점 

	@Column(nullable = false)
	private LocalDateTime start_at; // 루틴이 시작 시점

	@Column(nullable = false)
	private LocalDateTime end_at; // 루틴이 끝난 시점

	@Column(nullable = false)
	private String routine_desc_txt; //상세 루틴 설명 마크다운용

	@Builder
	private RoutineDetailEntity(LocalDateTime pre_event_at) {
		this.pre_event_at = pre_event_at;
	}

	// 예상 시점 업데이트하기  
	public void updatePreEventAt(LocalDateTime pre_event_at) {
		this.pre_event_at = pre_event_at;
	}

	// 시작 시간 업데이트 
	public void updateStartAt(LocalDateTime time) {
		this.start_at = time;
	}

	// 끝난 시간 업데이트 
	public void updateEndAt(LocalDateTime time) {
		this.end_at = time;
	}

	public void updateDescTxt(String txt) {
		this.routine_desc_txt = txt;
	}

}
