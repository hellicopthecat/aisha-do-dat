package com.hellicat.dodat.routines.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.hellicat.dodat.global.entity.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

	@Builder
	private RoutineEntity() {

	}

}
