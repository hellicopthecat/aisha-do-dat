package com.hellicat.dodat.routine_detail.dto.request;

import java.time.LocalDateTime;
import java.util.Set;

public class CreateRoutineDetailDto {

	public Long priorityTwoDepth;

	public LocalDateTime pre_event_start_at;

	public LocalDateTime pre_event_end_at;

	public LocalDateTime start_at;

	public LocalDateTime end_at;

	public String routine_desc_txt;

	public Set<String> tags;

}
