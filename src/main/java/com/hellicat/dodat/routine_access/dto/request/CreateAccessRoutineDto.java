package com.hellicat.dodat.routine_access.dto.request;

import java.util.UUID;

import com.hellicat.dodat.commons.enums.AccessEnum;

public record CreateAccessRoutineDto(
	AccessEnum status,
	UUID owner_id,
	UUID access_user_id,
	UUID routine_id) {

}
