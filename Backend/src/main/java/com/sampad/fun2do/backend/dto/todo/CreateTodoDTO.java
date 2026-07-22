package com.sampad.fun2do.backend.dto.todo;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.sampad.fun2do.backend.enums.Priority;

public class CreateTodoDTO {
	@NotNull(message = "Title is required")
	@NotBlank(message = "Title can not be blank!")
    private String title;
    private String description;
    @NotNull(message = "Priority is required. Accepted values are [LOW,MEDIUM,HIGH]")
    private Priority priority;

    private LocalDate dueDate;
    
    public CreateTodoDTO() {}
    
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Priority getPriority() {
		return priority;
	}
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
    
    
}
