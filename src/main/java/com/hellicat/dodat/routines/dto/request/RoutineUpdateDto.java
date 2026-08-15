package com.hellicat.dodat.routines.dto.request;

import com.hellicat.dodat.commons.enums.OneDepthEnums;

public record RoutineUpdateDto(
	String title,
	String desc,
	Long priorityOneDepth,
	OneDepthEnums category) {

}
