package com.hellicat.dodat.routines.dto.response;

import java.util.List;
import java.util.UUID;

import com.hellicat.dodat.commons.enums.OneDepthEnums;
import com.hellicat.dodat.routines.entity.RoutineEntity;

public record RoutineDto(
	UUID routineId,
	String title,
	String desc,
	long depth,
	OneDepthEnums category,
	UUID masterId,
	List<UUID> accessUserId) {
	public static RoutineDto from(RoutineEntity routine) {
		List<UUID> accessUser = routine.getRoutine_access().stream().map(user -> user.getAccess_user().getId()).toList();
		return new RoutineDto(
			routine.getId(),
			routine.getRoutine_title(),
			routine.getRoutine_desc(),
			routine.getPriorityOneDepth(),
			routine.getCategory(),
			routine.getUser().getId(),
			accessUser);
	}
}
