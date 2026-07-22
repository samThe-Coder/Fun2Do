package com.sampad.fun2do.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sampad.fun2do.backend.dto.todo.CreateTodoDTO;
import com.sampad.fun2do.backend.dto.todo.ResponseTodoDTO;
import com.sampad.fun2do.backend.dto.todo.UpdateTodoDTO;
import com.sampad.fun2do.backend.entity.Todo;
import com.sampad.fun2do.backend.entity.User;
import com.sampad.fun2do.backend.exception.todo.TodoNotFoundException;
import com.sampad.fun2do.backend.repository.TodoRepository;
import com.sampad.fun2do.backend.security.SecurityUtils;
import com.sampad.fun2do.backend.security.UserPrincipal;

@Service
public class TodoService {
	private static final Logger logger = LoggerFactory.getLogger(TodoService.class);
	
	private final TodoRepository todoRepo;
	public TodoService(TodoRepository todoRepo) {
		this.todoRepo = todoRepo;
	}
	
	private UUID getCurrentLoggedInUserId() {
		UserPrincipal principal = SecurityUtils.getCurrentUser();
		return principal.getId();
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
	
	public ResponseTodoDTO getTodoById(UUID id) {
		Todo todo = this.todoRepo.findByIdAndUser_Id(id,getCurrentLoggedInUserId()).orElseThrow(()-> new TodoNotFoundException("The requested item with id "+id+"  is not found!"));
		logger.debug("Retrieved todo with id: {} .",todo.getId());
		return mapToResponseDTO(todo);
	}
	
	public List<ResponseTodoDTO> getAllTodos(){
		List<ResponseTodoDTO> todos = todoRepo.findByUser_Id(getCurrentLoggedInUserId())
	            .stream()
	            .map(this::mapToResponseDTO)
	            .collect(Collectors.toList());
		logger.debug("Retrieved {} todos.",todos.size());
		return todos;
	} 
	
	public ResponseTodoDTO createTodo(CreateTodoDTO dto) {
		Todo todo = new Todo();
		todo.setTitle(dto.getTitle());
		todo.setDescription(dto.getDescription());
		todo.setPriority(dto.getPriority());
		todo.setDueDate(dto.getDueDate());
		
		User user = new User();
		user.setId(getCurrentLoggedInUserId());
		todo.setUser(user);
		
		Todo savedTodo = this.todoRepo.save(todo);
		logger.info("Todo created: {} by user: {}",savedTodo.getId(), getCurrentLoggedInUserId());
		return mapToResponseDTO(savedTodo);
	}
	
	public ResponseTodoDTO updateTodo(UUID id,UpdateTodoDTO dto) {
		Todo todo = todoRepo.findByIdAndUser_Id(id,getCurrentLoggedInUserId()).orElseThrow(()-> new TodoNotFoundException("The requested item with id "+id+"  is not found!"));
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
		logger.info("Todo updated: {} by user: {}",updatedTodo.getId(), getCurrentLoggedInUserId());
		return this.mapToResponseDTO(updatedTodo);
	}
	
	public void deleteTodo(UUID id) {
		Todo todoToBeDeleted = todoRepo.findByIdAndUser_Id(id,getCurrentLoggedInUserId()).orElseThrow(()-> new TodoNotFoundException("The requested item with id "+id+"  is not found!"));
//		todoRepo.deleteById(id);
		todoRepo.delete(todoToBeDeleted);
		logger.info("Todo deleted: {} by user: {}",todoToBeDeleted.getId(), getCurrentLoggedInUserId());
	}
}
