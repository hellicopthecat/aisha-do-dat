package com.hellicat.dodat.routine_access.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.hellicat.dodat.commons.enums.AccessEnum;
import com.hellicat.dodat.global.entity.BaseTimeEntity;
import com.hellicat.dodat.routines.entity.RoutineEntity;
import com.hellicat.dodat.users.entity.UserEntity;

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
public class RoutineAccessEntity extends BaseTimeEntity {

	@Id
	@UuidGenerator
	private UUID id;

	// 루틴의 접근 ENUM
	@Enumerated(EnumType.STRING)
	private AccessEnum can_access = AccessEnum.DENIED;

	@ManyToOne
	@JoinColumn(name = "owner_user_id", nullable = false)
	private UserEntity owner_user;

	@ManyToOne
	@JoinColumn(name = "access_user_id", nullable = false)
	private UserEntity access_user;

	@ManyToOne
	@JoinColumn(name = "routine_id", nullable = false)
	private RoutineEntity routine;

	@Builder
	private RoutineAccessEntity(UserEntity owner, UserEntity access, RoutineEntity routine) {
		this.owner_user = owner;
		this.access_user = access;
		this.routine = routine;
	}

	public void updateAccessStatus(AccessEnum status) {
		this.can_access = status;
	}
}
