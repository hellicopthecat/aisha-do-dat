package com.hellicat.dodat.auth.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hellicat.dodat.security.JwtTokenProvider;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final JwtTokenProvider jwtTokenProvider;

	public UUID getUserID(Cookie cookie) {
		UUID userId = jwtTokenProvider.parseUserId(cookie.getValue(), false);
		return userId;
	}

}
