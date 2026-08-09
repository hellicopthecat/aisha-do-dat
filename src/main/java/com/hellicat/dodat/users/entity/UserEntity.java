package com.hellicat.dodat.users.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.hellicat.dodat.commons.enums.SocialEnums;
import com.hellicat.dodat.global.entity.BaseTimeEntity;
import com.hellicat.dodat.routine_access.entity.RoutineAccessEntity;
import com.hellicat.dodat.routines.entity.RoutineEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_entity", uniqueConstraints = {
	@UniqueConstraint(name = "uk_user_provide_id", columnNames = {"provider_id"}),
	@UniqueConstraint(name = "uk_user_email", columnNames = {"email"})
})
public class UserEntity extends BaseTimeEntity {

	@Id
	@UuidGenerator
	private UUID id;

	@Column(nullable = false)
	private String email;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SocialEnums social;

	@Column(nullable = false)
	private String provider_id;

	@Column()
	private String refresh_token;

	@OneToMany(mappedBy = "user")
	private List<RoutineEntity> routines;

	@OneToMany(mappedBy = "access_user")
	private List<RoutineAccessEntity> access_routines = new ArrayList<RoutineAccessEntity>();

	@Builder
	private UserEntity(String email, SocialEnums social, String providerId) {
		this.email = email;
		this.social = social;
		this.provider_id = providerId;
	}

	public void updateRefreshToken(String token) {
		this.refresh_token = token;
	}

	public void clearRefreshToken() {
		this.refresh_token = null;
	}
}
