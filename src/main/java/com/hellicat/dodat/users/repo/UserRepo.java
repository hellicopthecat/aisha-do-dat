package com.hellicat.dodat.users.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hellicat.dodat.users.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, UUID> {

	Optional<UserEntity> findByEmail(String email);

}
