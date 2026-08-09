package com.hellicat.dodat.security;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hellicat.dodat.users.service.UserServiceImpl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

	private final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);
	private final UserServiceImpl userService;

	private String rf_key = System.getenv("REFRESH_TOKEN_KEY");
	private String at_key = System.getenv("ACCESS_TOKEN_KEY");

	private final long expired_at = 1000l * 60 * 60 * 24 * 7;
	private final long expired_rf = 1000l * 60 * 60 * 24 * 30;

	@PostConstruct
	protected void init() {
		rf_key = Base64.getEncoder().encodeToString(rf_key.getBytes());
		at_key = Base64.getEncoder().encodeToString(at_key.getBytes());
	}

	public String createRefreshToken(UUID userId) {
		Date now = new Date();
		String rt = Jwts.builder().subject(userId.toString())
			.issuedAt(now)
			.expiration(new Date(System.currentTimeMillis() + expired_rf))
			.signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(rf_key))).compact();
		return rt;
	}

	public String createAccessToken(UUID userId) {
		Date now = new Date();
		String at = Jwts.builder().subject(userId.toString())
			.issuedAt(now)
			.expiration(new Date(System.currentTimeMillis() + expired_at))
			.signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(at_key))).compact();
		return at;
	}

	// validate 토큰 

	// 엑세스토큰 추출

	// 리프레시토큰 추출 

	// 엑세스토큰 연장 

}
