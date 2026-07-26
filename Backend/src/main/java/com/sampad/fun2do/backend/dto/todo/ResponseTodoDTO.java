package com.sampad.fun2do.backend.dto.todo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sampad.fun2do.backend.enums.Priority;

public class ResponseTodoDTO {
	private UUID id;
    private String title;
    private String description;
    private Priority priority;
    private LocalDate dueDate;
    private boolean  isCompleted;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private long daysRemaining;

    public void setDaysRemaining(long days) {
    	this.daysRemaining=days;
    }
    public long getDaysRemaining() {
    	return this.daysRemaining;
    }
    public ResponseTodoDTO() {}
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

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
	@JsonProperty("isCompleted")
	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getCompletedAt() {
		return completedAt;
	}

	public void setIsCompletedAt(LocalDateTime completedAt) {
		this.completedAt = completedAt;
	}
}
