package com.hellicat.dodat.routine_tags.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.hellicat.dodat.global.entity.BaseTimeEntity;
import com.hellicat.dodat.routine_detail.entity.RoutineDetailEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoutineTagEntity extends BaseTimeEntity {

	@Id
	@UuidGenerator
	private UUID id;

	@Column
	private String tag;

	@ManyToMany(mappedBy = "tags")
	private List<RoutineDetailEntity> routineDetails = new ArrayList<RoutineDetailEntity>();

	@Builder
	public RoutineTagEntity(String tag, RoutineDetailEntity detail) {
		this.tag = tag;
		this.routineDetails.add(detail);
	}

	public void updateTag(String tag) {
		this.tag = tag;
	}

}
