package com.sampad.fun2do.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sampad.fun2do.backend.entity.Todo;
import com.sampad.fun2do.backend.service.TodoService;

@RestController
@RequestMapping("/api/v1/todo")

public class TodoController {
	private final TodoService todoServ;
	public TodoController(TodoService todoServ) {
		this.todoServ=todoServ;
	}
	@GetMapping
	public List<Todo> getAllTodos() {
		return todoServ.getAllTodos();
	}

}
