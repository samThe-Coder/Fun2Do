package com.sampad.fun2do.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sampad.fun2do.backend.entity.Todo;
import com.sampad.fun2do.backend.repository.TodoRepository;

@Service
public class TodoService {
	private final TodoRepository todoRepo;
	public TodoService(TodoRepository todoRepo) {
		this.todoRepo = todoRepo;
	}
	public List<Todo> getAllTodos(){
		return todoRepo.findAll();
	} 

}
