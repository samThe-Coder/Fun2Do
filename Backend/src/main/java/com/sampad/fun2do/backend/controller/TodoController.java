package com.sampad.fun2do.backend.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sampad.fun2do.backend.dto.todo.CreateTodoDTO;
import com.sampad.fun2do.backend.dto.todo.ResponseTodoDTO;
import com.sampad.fun2do.backend.dto.todo.UpdateTodoDTO;
import com.sampad.fun2do.backend.service.TodoService;

@RestController
@RequestMapping("/api/v1/todos")

public class TodoController {
	private final TodoService todoServ;
	public TodoController(TodoService todoServ) {
		this.todoServ=todoServ;
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ResponseTodoDTO> getTodoById(@PathVariable UUID id){
		return ResponseEntity.ok(todoServ.getTodoById(id));
	}
	@GetMapping
	public ResponseEntity<Page<ResponseTodoDTO>> getAllTodos( @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		return ResponseEntity.ok(todoServ.getAllTodos(pageable));
	}
	@PostMapping
	public ResponseEntity<ResponseTodoDTO> createTodo(@Valid @RequestBody  CreateTodoDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(todoServ.createTodo(dto));
	}
	@PatchMapping("{id}")
	public ResponseEntity<ResponseTodoDTO> updateTodo(@PathVariable UUID id, @Valid @RequestBody UpdateTodoDTO dto) {
		return ResponseEntity.ok(todoServ.updateTodo(id,dto));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable UUID id){
		todoServ.deleteTodo(id);
		return ResponseEntity.noContent().build();
	}
}
