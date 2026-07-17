package com.sampad.fun2do.backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sampad.fun2do.backend.entity.Todo;
@Repository
public interface TodoRepository extends JpaRepository<Todo,UUID>{

}
