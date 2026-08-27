package com.hellicat.dodat.routine_access.dto.response;

import java.util.UUID;

import com.hellicat.dodat.commons.enums.AccessEnum;
import com.hellicat.dodat.commons.enums.AccessRoleEnum;
import com.hellicat.dodat.routine_access.entity.RoutineAccessEntity;
import com.hellicat.dodat.users.dto.response.UserResponseDto;

public record AccessRoutineResponseDto(
	UUID id,
	AccessEnum accessEnum,
	AccessRoleEnum accessrole,
	UserResponseDto owner,
	UserResponseDto accessUser,
	UUID routineId

) {
	public static final AccessRoutineResponseDto from(RoutineAccessEntity access) {
		return new AccessRoutineResponseDto(
			access.getId(),
			access.getCan_access(),
			access.getEditable(),
			UserResponseDto.from(access.getOwner_user()),
			UserResponseDto.from(access.getAccess_user()),
			access.getRoutine().getId());
	}
}
