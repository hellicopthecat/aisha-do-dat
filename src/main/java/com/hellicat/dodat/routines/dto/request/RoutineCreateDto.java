package com.hellicat.dodat.routines.dto.request;

import java.util.List;

import com.hellicat.dodat.commons.enums.OneDepthEnums;
import com.hellicat.dodat.routine_detail.dto.request.CreateRoutineDetailDto;

public record RoutineCreateDto(

	String title,

	String desc,

	OneDepthEnums category,

	List<CreateRoutineDetailDto> detail) {

}
