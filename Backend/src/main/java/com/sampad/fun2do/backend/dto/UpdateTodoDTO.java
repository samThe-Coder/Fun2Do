package com.sampad.fun2do.backend.dto;

import java.time.LocalDate;

import com.sampad.fun2do.backend.enums.Priority;

public class UpdateTodoDTO {
    private String title;
    private String description;
    private Priority priority;
    private LocalDate dueDate;
    private Boolean isCompleted;
//    private LocalDateTime completedAt;
    
    public UpdateTodoDTO() {}
    
//	public UUID getId() {
//		return id;
//	}
//
//	public void setId(UUID id) {
//		this.id = id;
//	}
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
	public Boolean isCompleted() {
		return isCompleted;
	}
	public void setIsCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

//	public LocalDateTime getCompletedAt() {
//		return completedAt;
//	}
//
//	public void setCompletedAt(LocalDateTime completedAt) {
//		this.completedAt = completedAt;
//	}
}
