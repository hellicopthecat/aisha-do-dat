package com.hellicat.dodat.users.service;

import java.util.UUID;

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
		return userRepo.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(email));
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
	public void logoutUser(UUID id) {
		UserEntity user = userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
		user.clearRefreshToken();
	}
}
