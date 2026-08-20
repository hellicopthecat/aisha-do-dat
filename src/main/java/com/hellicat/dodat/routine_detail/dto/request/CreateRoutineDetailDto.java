package com.hellicat.dodat.routine_detail.dto.request;

import java.time.LocalDateTime;
import java.util.List;

public record CreateRoutineDetailDto(

	Long priorityTwoDepth,

	LocalDateTime pre_event_start_at,

	LocalDateTime pre_event_end_at,

	LocalDateTime start_at,

	LocalDateTime end_at,

	String routine_desc_txt,

	List<String> tags

) {

}
