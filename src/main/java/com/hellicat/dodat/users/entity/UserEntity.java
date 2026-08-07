package com.hellicat.dodat.users.entity;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.hellicat.dodat.global.entity.BaseTimeEntity;
import com.hellicat.dodat.routines.entity.RoutineEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseTimeEntity {

	@Id
	@UuidGenerator
	private UUID id;

	@Column
	private String email;

	@Column(nullable = false)
	private String refresh_token;

	private List<RoutineEntity> routines;

	@Builder
	private UserEntity(String user_id) {
		this.email = user_id;
	}

	public void updateRefreshToken(String token) {
		this.refresh_token = token;
	}

}
