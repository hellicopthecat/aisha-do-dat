package com.hellicat.dodat.routines.entity;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.hellicat.dodat.commons.enums.AccessEnum;
import com.hellicat.dodat.commons.enums.OneDepthEnums;
import com.hellicat.dodat.global.entity.BaseTimeEntity;
import com.hellicat.dodat.routine_access.entity.RoutineAccessEntity;
import com.hellicat.dodat.routine_detail.entitny.RoutineDetailEntity;
import com.hellicat.dodat.users.entity.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class RoutineEntity extends BaseTimeEntity {

	@Id
	@UuidGenerator
	private UUID id;

	//루틴 타이틀
	@Column
	private String routine_title;

	//루틴 설명
	@Column(nullable = false)
	private String routine_desc;

	// 1차 그룹 
	@Enumerated(EnumType.STRING)
	private OneDepthEnums one_depth_group = OneDepthEnums.ALL;

	// 루틴의 접근 ENUM
	@Enumerated(EnumType.STRING)
	private AccessEnum can_access = AccessEnum.DENIED;

	// 루틴의 상세 
	private List<RoutineDetailEntity> routine_detail;

	// 루틴 접근 명단
	private List<RoutineAccessEntity> access_user;

	// 루틴의 주인(유저) 
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@Builder
	private RoutineEntity(String title, String dsec, List<RoutineDetailEntity> routines) {

		this.routine_title = title;
		this.routine_desc = dsec;
		this.routine_detail = routines;

	}

	public void updateRoutineDetail(RoutineDetailEntity routine) {
		this.routine_detail.add(routine);
	}

}
