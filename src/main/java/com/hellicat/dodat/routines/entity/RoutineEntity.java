package com.hellicat.dodat.routines.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.hellicat.dodat.commons.enums.OneDepthEnums;
import com.hellicat.dodat.global.entity.BaseTimeEntity;
import com.hellicat.dodat.routine_access.entity.RoutineAccessEntity;
import com.hellicat.dodat.routine_detail.entity.RoutineDetailEntity;
import com.hellicat.dodat.users.entity.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	@Column(nullable = false)
	private String routine_title;

	//루틴 설명
	@Column
	private String routine_desc;

	// 1차 그룹 
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OneDepthEnums category = OneDepthEnums.ALL;

	// 루틴의 상세 
	@OneToMany(mappedBy = "routine")
	private List<RoutineDetailEntity> routine_detail = new ArrayList<RoutineDetailEntity>();

	// 루틴의 주인(유저) 
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;

	// 루틴의 접근자 
	@OneToMany(mappedBy = "routine")
	private List<RoutineAccessEntity> routine_access = new ArrayList<RoutineAccessEntity>();

	@Builder
	private RoutineEntity(
		String title,
		String dsec,
		OneDepthEnums category,
		UserEntity user) {

		this.routine_title = title;
		this.routine_desc = dsec;
		this.category = category;
		this.user = user;

	}

	public void updateRoutineTitle(String title) {
		this.routine_title = title;
	}

	public void updateRoutineDesc(String desc) {
		this.routine_desc = desc;
	}

	public void updateRoutineCategory(OneDepthEnums category) {
		this.category = category;
	}

	public void initRoutineDetail(List<RoutineDetailEntity> routines) {
		this.routine_detail = routines;
	}

	public void addRoutineDetail(RoutineDetailEntity detail) {
		this.routine_detail.add(detail);
	}
}
