package com.hellicat.dodat.users.service;

import org.springframework.stereotype.Service;

import com.hellicat.dodat.users.entity.UserEntity;

@Service
public interface UserService {

	UserEntity createUser(UserEntity user);

	UserEntity findUserByEmail(String email);

	UserEntity getUserByEmail(String email);

	void updateRefreshToken(String email, String refreshToken);

}
