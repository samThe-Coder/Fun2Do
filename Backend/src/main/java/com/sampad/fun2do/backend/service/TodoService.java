package com.sampad.fun2do.backend.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sampad.fun2do.backend.dto.todo.CreateTodoDTO;
import com.sampad.fun2do.backend.dto.todo.ResponseTodoDTO;
import com.sampad.fun2do.backend.dto.todo.UpdateTodoDTO;
import com.sampad.fun2do.backend.entity.Todo;
import com.sampad.fun2do.backend.entity.User;
import com.sampad.fun2do.backend.enums.Priority;
import com.sampad.fun2do.backend.exception.todo.TodoNotFoundException;
import com.sampad.fun2do.backend.repository.TodoRepository;
import com.sampad.fun2do.backend.security.SecurityUtils;
import com.sampad.fun2do.backend.security.UserPrincipal;
import com.sampad.fun2do.backend.specification.TodoSpecification;

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
	
	public Page<ResponseTodoDTO> getAllTodos(Priority priority, Boolean isCompleted, String keyword, Pageable pageable){
		Specification <Todo> specification = Specification.where(
					TodoSpecification.belongsToUser(getCurrentLoggedInUserId())
					).and(TodoSpecification.hasPriority(priority))
					.and(TodoSpecification.isCompleted(isCompleted))
					.and(TodoSpecification.containsKeyword(keyword));
				
		logger.debug("Pageable: {}",pageable);
		logger.debug("Sort: {}",pageable.getSort());
		if(pageable.getSort().isUnsorted()) {
			pageable = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),Sort.by(Sort.Direction.DESC,"createdAt"));
		}
		
		int pageSize = Math.min(pageable.getPageSize(), 100);
		Pageable safePageable = PageRequest.of(pageable.getPageNumber(),pageSize,pageable.getSort());
		
		Page<ResponseTodoDTO> todos = todoRepo.findAll(specification,safePageable)
//	            .stream()
	            .map(this::mapToResponseDTO);
//	            .collect(Collectors.toList());
		logger.debug("Retrieved {} todos.",todos.getTotalElements());
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
