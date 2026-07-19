package com.sampad.fun2do.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sampad.fun2do.backend.dto.CreateTodoDTO;
import com.sampad.fun2do.backend.dto.ResponseTodoDTO;
import com.sampad.fun2do.backend.dto.UpdateTodoDTO;
import com.sampad.fun2do.backend.entity.Todo;
import com.sampad.fun2do.backend.repository.TodoRepository;

@Service
public class TodoService {
	private final TodoRepository todoRepo;
	public TodoService(TodoRepository todoRepo) {
		this.todoRepo = todoRepo;
	}
	private ResponseTodoDTO mapToResponseDTO(Todo todo) {
	    ResponseTodoDTO dto = new ResponseTodoDTO();

	    dto.setId(todo.getId());
	    dto.setTitle(todo.getTitle());
	    dto.setDescription(todo.getDescription());
	    dto.setPriority(todo.getPriority());
	    dto.setDueDate(todo.getDueDate());
	    dto.setCompleted(todo.isCompleted());
	    dto.setCreatedAt(todo.getCreatedAt());
	    dto.setIsCompletedAt(todo.getCompletedAt());
	    dto.setDaysRemaining(todo.getDaysRemaining());

	    return dto;
	}
	public List<ResponseTodoDTO> getAllTodos(){
		return todoRepo.findAll()
	            .stream()
	            .map(this::mapToResponseDTO)
	            .collect(Collectors.toList());
	} 
	
	public ResponseTodoDTO createTodo(CreateTodoDTO dto) {
		Todo todo = new Todo();
		todo.setTitle(dto.getTitle());
		todo.setDescription(dto.getDescription());
		todo.setPriority(dto.getPriority());
		todo.setDueDate(dto.getDueDate());
		
		Todo savedTodo = this.todoRepo.save(todo);
		return mapToResponseDTO(savedTodo);
	}
	
	public ResponseTodoDTO updateTodo(UUID id,UpdateTodoDTO dto) {
		Todo todo = todoRepo.findById(id).orElseThrow(()-> new RuntimeException("The requested TODO is not found!"));
		if(dto.getTitle() != null && !dto.getTitle().trim().isEmpty()) {
			todo.setTitle(dto.getTitle());
		}
		if(dto.getDescription() != null && !dto.getDescription().trim().isEmpty()) {
			todo.setDescription(dto.getDescription());
		}
		if(dto.getPriority() != null ) {
			todo.setPriority(dto.getPriority());
		}
		if(dto.getDueDate() != null ) {
			todo.setDueDate(dto.getDueDate());
		}
		if(dto.isCompleted() != null) {
			if(dto.isCompleted() && !todo.isCompleted()) {
				todo.setCompletedAt(LocalDateTime.now());
			}else if(!dto.isCompleted()){
				todo.setCompletedAt(null);
			}
			todo.setIsCompleted(dto.isCompleted());
		}
		
		
		Todo updatedTodo = this.todoRepo.save(todo);
		return this.mapToResponseDTO(updatedTodo);
	}
	
	public void deleteTodo(UUID id) {
		Todo todoToBeDeleted = todoRepo.findById(id).orElseThrow(()-> new RuntimeException("The requested item was not found!"));
//		todoRepo.deleteById(id);
		todoRepo.delete(todoToBeDeleted);
	}
}
