package com.sampad.fun2do.backend.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.sampad.fun2do.backend.entity.Todo;
@Repository
public interface TodoRepository extends JpaRepository<Todo,UUID>,JpaSpecificationExecutor<Todo>{

	Page<Todo> findByUser_Id(UUID userId, Pageable pageable);
    Optional<Todo> findByIdAndUser_Id(UUID id, UUID userId);
}
