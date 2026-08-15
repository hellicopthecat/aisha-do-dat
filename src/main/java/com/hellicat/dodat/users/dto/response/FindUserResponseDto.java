package com.hellicat.dodat.users.dto.response;

import java.util.UUID;

import com.hellicat.dodat.users.entity.UserEntity;

public record FindUserResponseDto(
	UUID id,
	String email) {
	public static FindUserResponseDto from(UserEntity user) {
		return new FindUserResponseDto(
			user.getId(),
			user.getEmail());
	}
}
