package com.hellicat.dodat.routine_detail.dto.request;

import java.time.LocalDateTime;

public class UpdateRoutineDetailDto {

	public LocalDateTime pre_event_start_at; // 루틴 예상 시점 

	public LocalDateTime pre_event_end_at; // 루틴 예상 시점

	public LocalDateTime start_at; // 루틴이 시작 시점

	public LocalDateTime end_at; // 루틴이 끝난 시점

	public String routine_desc_txt; //상세 루틴 설명 마크다운용

}
