package com.hellicat.dodat.users.dto.response;

import java.util.UUID;

import com.hellicat.dodat.users.entity.UserEntity;

public record UserResponseDto(
	UUID id,
	String email) {
	public static UserResponseDto from(UserEntity user) {
		return new UserResponseDto(
			user.getId(),
			user.getEmail());
	}
}
