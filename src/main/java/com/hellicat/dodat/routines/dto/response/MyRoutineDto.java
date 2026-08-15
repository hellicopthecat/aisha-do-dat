package com.hellicat.dodat.routines.dto.response;

import java.util.List;
import java.util.UUID;

import com.hellicat.dodat.commons.enums.OneDepthEnums;
import com.hellicat.dodat.routines.entity.RoutineEntity;

public record MyRoutineDto(
	UUID routineId,
	String title,
	String desc,
	long depth,
	OneDepthEnums category,
	UUID masterId,
	List<UUID> accessUserId) {

	public static List<MyRoutineDto> from(List<RoutineEntity> routines) {

		List<MyRoutineDto> myRoutines;

		myRoutines = routines.stream().map(r -> {
			List<UUID> accessUser = r.getRoutine_access().stream().map(user -> user.getAccess_user().getId()).toList();
			return new MyRoutineDto(
				r.getId(),
				r.getRoutine_title(),
				r.getRoutine_desc(),
				r.getPriorityOneDepth(),
				r.getCategory(),
				r.getUser().getId(),
				accessUser);
		}).toList();

		return myRoutines;
	}

}
