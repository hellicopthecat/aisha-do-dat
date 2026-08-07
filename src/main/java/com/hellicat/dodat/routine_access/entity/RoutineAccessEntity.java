package com.hellicat.dodat.routine_access.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.hellicat.dodat.global.entity.BaseTimeEntity;
import com.hellicat.dodat.routines.entity.RoutineEntity;
import com.hellicat.dodat.users.entity.UserEntity;

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
public class RoutineAccessEntity extends BaseTimeEntity {

	@Id
	@UuidGenerator
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;

	@ManyToOne
	@JoinColumn(name = "routine_id", nullable = false)
	private RoutineEntity routine;

	@Builder
	private RoutineAccessEntity(UserEntity user, RoutineEntity routine) {
		this.user = user;
		this.routine = routine;
	}

}
