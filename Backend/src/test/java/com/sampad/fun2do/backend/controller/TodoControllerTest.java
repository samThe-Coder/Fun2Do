package com.sampad.fun2do.backend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sampad.fun2do.backend.dto.todo.CreateTodoDTO;
import com.sampad.fun2do.backend.dto.todo.ResponseTodoDTO;
import com.sampad.fun2do.backend.enums.Priority;
import com.sampad.fun2do.backend.service.TodoService;

@WebMvcTest(TodoController.class)
public class TodoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TodoService todoServ;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void shouldReturnAllTodos() throws Exception{
		ResponseTodoDTO todo = new ResponseTodoDTO();	
		todo.setTitle("Learn AI Tools");
		Page<ResponseTodoDTO> page = new PageImpl<>(Arrays.asList(todo));
		when(todoServ.getAllTodos(any(Pageable.class))).thenReturn(page);
		
		mockMvc.perform(get("/api/v1/todos"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content[0].title").value("Learn AI Tools"));
	}
	
	@Test
	void shouldCreateATodoFromDTO() throws Exception {
		CreateTodoDTO dto = new CreateTodoDTO();
		dto.setTitle("Learn AI Tools");
		dto.setPriority(Priority.MEDIUM);
		dto.setDescription("Integrate AI tools with Spring Boot projects");
		dto.setDueDate(LocalDate.now().plusDays(2));
		
		ResponseTodoDTO savedTodo = new ResponseTodoDTO();
		savedTodo.setTitle(dto.getTitle());
		savedTodo.setDescription(dto.getDescription());
		savedTodo.setPriority(dto.getPriority());
		savedTodo.setDueDate(dto.getDueDate());
		
		
		when(todoServ.createTodo(any(CreateTodoDTO.class))).thenReturn(savedTodo);
		
		mockMvc.perform(post("/api/v1/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.title").value("Learn AI Tools"))
				.andExpect(jsonPath("$.description").value("Integrate AI tools with Spring Boot projects"))
				.andExpect(jsonPath("$.priority").value(dto.getPriority().name()))
				.andExpect(jsonPath("$.dueDate").value(LocalDate.now().plusDays(2).toString()))
				;
		
		ArgumentCaptor<CreateTodoDTO> captor = ArgumentCaptor.forClass(CreateTodoDTO.class);
		verify(todoServ,times(1)).createTodo(captor.capture());
		
		CreateTodoDTO capturedDTO = captor.getValue();
		assertEquals("Learn AI Tools",capturedDTO.getTitle());
		assertEquals(Priority.MEDIUM,capturedDTO.getPriority());
	}
	
	@Test
	void shouldReturnBadRequestWhenTitleIsMissing() throws Exception {
		CreateTodoDTO dto = new CreateTodoDTO();
//		dto.setTitle("Learn AI Tools");
		dto.setDescription("Integrate AI tools with Spring Boot projects");
		dto.setPriority(Priority.MEDIUM);
		dto.setDueDate(LocalDate.now().plusDays(2));
		
		
//		when(todoServ.createTodo(any(CreateTodoDTO.class))).thenReturn(savedTodo); // Spring validations runs before the controller method calls the service
		
		mockMvc.perform(post("/api/v1/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
				.andExpect(status().isBadRequest());
		verifyNoInteractions(todoServ);
	}
	@Test
	void shouldReturnBadRequestWhenPriorityIsInvalid() throws Exception {
		Map<String, String> request = new HashMap<>();
		request.put("title", "Learn AI Tools");
		request.put("description", "Integrate AI tools with Spring Boot projects");
		request.put("priority", "URGENT");   // invalid enum
		request.put("dueDate", "2026-07-21");
		
		mockMvc.perform(post("/api/v1/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				).andExpect(status().isBadRequest());
		verifyNoInteractions(todoServ);

	}
	
	
}
