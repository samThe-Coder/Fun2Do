package com.sampad.fun2do.backend.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sampad.fun2do.backend.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {
	Optional<User> findByUserName(String userName);
}
