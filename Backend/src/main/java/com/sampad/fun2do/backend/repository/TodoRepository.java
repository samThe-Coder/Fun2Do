package com.sampad.fun2do.backend.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sampad.fun2do.backend.entity.Todo;
@Repository
public interface TodoRepository extends JpaRepository<Todo,UUID>{

	List<Todo> findByUser_Id(UUID userId);
    Optional<Todo> findByIdAndUser_Id(UUID id, UUID userId);
}
