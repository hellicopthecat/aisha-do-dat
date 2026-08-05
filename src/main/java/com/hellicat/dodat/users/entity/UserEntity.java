package com.hellicat.dodat.users.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

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
public class UserEntity {
	@Id
	@UuidGenerator
	private UUID id;

	private String user_id;

	@Column(nullable = true)
	private String refresh_token;

	@Builder
	private UserEntity(String user_id) {
		this.user_id = user_id;
	}

	public void updateRefreshToken(String token) {
		this.refresh_token = token;
	}
}
