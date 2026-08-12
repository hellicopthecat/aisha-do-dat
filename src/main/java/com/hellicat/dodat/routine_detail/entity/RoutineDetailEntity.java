package com.hellicat.dodat.routine_detail.entity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.hellicat.dodat.global.entity.BaseTimeEntity;
import com.hellicat.dodat.routine_tags.entity.RoutineTagEntity;
import com.hellicat.dodat.routines.entity.RoutineEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

	@Column
	private long priorityTwoDepth;

	@Column(nullable = false)
	private LocalDateTime pre_event_start_at; // 루틴 예상 시점 

	@Column(nullable = false)
	private LocalDateTime pre_event_end_at; // 루틴 예상 시점

	@Column
	private LocalDateTime start_at; // 루틴이 시작 시점

	@Column
	private LocalDateTime end_at; // 루틴이 끝난 시점

	@Column
	private String routine_desc_txt; //상세 루틴 설명 마크다운용

	@ManyToMany
	@JoinTable(name = "routine_detail_tag", joinColumns = @JoinColumn(name = "routine_detail_id"), inverseJoinColumns = @JoinColumn(name = "routine_tag_id"))
	private Set<RoutineTagEntity> tags; // 루틴 태그 

	@ManyToOne
	@JoinColumn(name = "routine_id")
	private RoutineEntity routine;

	@Builder
	private RoutineDetailEntity(LocalDateTime pre_event_start_at, LocalDateTime pre_event_end_at) {
		this.pre_event_start_at = pre_event_start_at;
		this.pre_event_end_at = pre_event_end_at;
	}

	// 예상 시점 업데이트하기  
	public void updatePreEventStartAt(LocalDateTime pre_event_start_at) {
		this.pre_event_start_at = pre_event_start_at;
	}

	//예상 시점 업데이트하기  
	public void updatePreEventEndAt(LocalDateTime pre_event_end_at) {
		this.pre_event_end_at = pre_event_end_at;
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
