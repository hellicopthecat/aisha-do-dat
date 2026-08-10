package com.hellicat.dodat.social.google.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GoogleUserResponse(
	@JsonProperty("email")
	String email,
	@JsonProperty("sub")
	String sub,
	@JsonProperty("name")
	String name) {}
