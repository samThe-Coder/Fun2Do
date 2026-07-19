package com.sampad.fun2do.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sampad.fun2do.backend.dto.CreateTodoDTO;
import com.sampad.fun2do.backend.dto.ResponseTodoDTO;
import com.sampad.fun2do.backend.entity.Todo;
import com.sampad.fun2do.backend.enums.Priority;
import com.sampad.fun2do.backend.repository.TodoRepository;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {
	@Mock
	private TodoRepository todoRepo;
	@InjectMocks
	private TodoService todoServ;
	
	@Test
	void shouldReturnAllTodos() {
		Todo todo = new Todo();
		todo.setTitle("Learn AI Tools");
		when(todoRepo.findAll()).thenReturn(Arrays.asList(todo));
		
		List<ResponseTodoDTO> result = todoServ.getAllTodos();
		assertEquals(1,result.size());
		assertEquals("Learn AI Tools",result.get(0).getTitle());
		verify(todoRepo).findAll();
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

}
