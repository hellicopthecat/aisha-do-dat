package com.hellicat.dodat.routines.dto.response;

import java.util.List;
import java.util.UUID;

import com.hellicat.dodat.commons.enums.OneDepthEnums;
import com.hellicat.dodat.routines.entity.RoutineEntity;

public record MyRoutineResponseDto(
	UUID routineId,
	String title,
	String desc,
	long depth,
	OneDepthEnums category,
	UUID masterId,
	List<UUID> accessUserId) {

	public static List<MyRoutineResponseDto> from(List<RoutineEntity> routines) {

		List<MyRoutineResponseDto> myRoutines;

		myRoutines = routines.stream().map(r -> {
			List<UUID> accessUser = r.getRoutine_access().stream().map(user -> user.getAccess_user().getId()).toList();
			return new MyRoutineResponseDto(
				r.getId(),
				r.getRoutine_title(),
				r.getRoutine_desc(),
				r.getPriorityOneDepth(),
				r.getCategory(),
				r.getOwner().getId(),
				accessUser);
		}).toList();

		return myRoutines;
	}

}
