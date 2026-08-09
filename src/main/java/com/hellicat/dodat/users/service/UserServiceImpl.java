package com.hellicat.dodat.users.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hellicat.dodat.users.entity.UserEntity;
import com.hellicat.dodat.users.repo.UserRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepo userRepo;

	public UserServiceImpl(UserRepo repo) {
		this.userRepo = repo;
	}

	public UserEntity createUser(UserEntity user) {
		return userRepo.save(user);
	}

	public UserEntity findUserByEmail(String email) {
		return userRepo.findByEmail(email).orElse(null);
	}

	public UserEntity getUserByEmail(String email) {
		return userRepo.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(email));
	}

	@Transactional
	public void updateRefreshToken(String email, String refreshToken) {
		UserEntity user = getUserByEmail(email);
		user.updateRefreshToken(refreshToken);
	}

	@Transactional
	public void logoutUser(String email) {
		UserEntity user = getUserByEmail(email);
		user.clearRefreshToken();
	}
}
