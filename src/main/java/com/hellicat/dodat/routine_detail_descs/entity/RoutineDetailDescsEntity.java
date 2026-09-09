package com.hellicat.dodat.routine_detail_descs.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.hellicat.dodat.global.entity.BaseTimeEntity;
import com.hellicat.dodat.routine_detail.entity.RoutineDetailEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoutineDetailDescsEntity extends BaseTimeEntity {

	@Id
	@UuidGenerator
	private UUID id;

	@Column
	private String desc;

	@ManyToOne
	@JoinColumn(name = "routine_detail_id")
	private RoutineDetailEntity routine_detail;

}
