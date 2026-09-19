package com.hellicat.dodat.users.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hellicat.dodat.users.entity.UserEntity;

@Service
public interface UserService {

	UserEntity createUser(UserEntity user);

	UserEntity findUserByEmail(String email);

	void updateRefreshToken(String email, String refreshToken);

	void deleteUser(UUID id);

}
