package com.sampad.fun2do.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sampad.fun2do.backend.dto.todo.CreateTodoDTO;
import com.sampad.fun2do.backend.dto.todo.ResponseTodoDTO;
import com.sampad.fun2do.backend.entity.Todo;
import com.sampad.fun2do.backend.enums.Priority;
import com.sampad.fun2do.backend.enums.Role;
import com.sampad.fun2do.backend.exception.todo.TodoNotFoundException;
import com.sampad.fun2do.backend.repository.TodoRepository;
import com.sampad.fun2do.backend.security.UserPrincipal;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {
	@Mock
	private TodoRepository todoRepo;
	@InjectMocks
	private TodoService todoServ;
	private final UUID userId = UUID.randomUUID();
	 
	@BeforeEach
	void mockAuthenticatedUser() {
		UserPrincipal principal = new UserPrincipal(userId,"sampad",Role.USER);
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principal,null, principal.getAuthorities()));
	}
	
	@Test
	void shouldReturnAllTodos() {
		Todo todo = new Todo();
		todo.setTitle("Learn AI Tools");
		Page<Todo> page = new PageImpl<>(Arrays.asList(todo));
		when(todoRepo.findByUser_Id(eq(userId),any(Pageable.class))).thenReturn(page);
		
		Page<ResponseTodoDTO> result = todoServ.getAllTodos(PageRequest.of(0, 10));
		assertEquals(1,result.getTotalElements());
		assertEquals("Learn AI Tools",result.getContent().get(0).getTitle());
		verify(todoRepo,times(1)).findByUser_Id(eq(userId),any(Pageable.class));
	}
	
	@Test
	void shouldReturnTodoById() {
		UUID todoId = UUID.randomUUID();
		Todo todo = new Todo();
		todo.setId(todoId);
		todo.setTitle("Learn AI Tools");
		when(todoRepo.findByIdAndUser_Id(todoId, userId)).thenReturn(Optional.of(todo));
		
		ResponseTodoDTO result = todoServ.getTodoById(todoId);
		
		assertEquals(todoId,result.getId());
		assertEquals("Learn AI Tools",result.getTitle());
		verify(todoRepo,times(1)).findByIdAndUser_Id(todoId, userId);
	}
	
	@Test
	void shouldThrowTodoNotFoundException() {
		UUID todoId = UUID.randomUUID();
		
		when(todoRepo.findByIdAndUser_Id(todoId, userId)).thenReturn(Optional.empty());
		assertThrows(TodoNotFoundException.class,()-> todoServ.getTodoById(todoId));
		
	}
	
	@Test
	void shouldCreateATodoFromDTO() {
		CreateTodoDTO dto = new CreateTodoDTO();
		dto.setTitle("Learn AI Tools");
		dto.setPriority(Priority.MEDIUM);
		dto.setDescription("Integrate AI tools with Spring Boot projects");
		dto.setDueDate(LocalDate.now().plusDays(2));
		
		Todo savedTodo = new Todo();
		savedTodo.setTitle(dto.getTitle());
		savedTodo.setDescription(dto.getDescription());
		savedTodo.setPriority(dto.getPriority());
		savedTodo.setDueDate(dto.getDueDate());
				
		when(todoRepo.save(any(Todo.class))).thenReturn(savedTodo);
		
		ResponseTodoDTO returnedTodo = todoServ.createTodo(dto);
		assertNotNull(returnedTodo);
		assertEquals("Learn AI Tools", returnedTodo.getTitle());
		
		ArgumentCaptor<Todo> captor = ArgumentCaptor.forClass(Todo.class); //To check if the service correctly mapped the DTO to the entity
		verify(todoRepo,times(1)).save(captor.capture());
		
		Todo capturedTodo = captor.getValue();
		
		assertEquals(dto.getTitle(),capturedTodo.getTitle());
		assertEquals(dto.getDueDate(),capturedTodo.getDueDate());
		assertEquals(Priority.MEDIUM,capturedTodo.getPriority());
		assertEquals("Integrate AI tools with Spring Boot projects",capturedTodo.getDescription());
		
	}
	@AfterEach
	void cleanup(){
	    SecurityContextHolder.clearContext();
	}
}
