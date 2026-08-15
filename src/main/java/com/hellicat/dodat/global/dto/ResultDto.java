package com.hellicat.dodat.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultDto<T> {
	private String message;
	private String code;
	private T data;

	public static <T> ResultDto<T> success(String message, T data) {
		return new ResultDto<>(message, "SUCCESS", data);
	}

	public static <T> ResultDto<T> fail(String message, String code) {
		return new ResultDto<>(message, code, null);
	}
}
