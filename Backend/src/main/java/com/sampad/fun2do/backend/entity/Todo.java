package com.sampad.fun2do.backend.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="todo")
public class Todo {
	@Id
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;
	@NotNull
	@Column(nullable=false)
    private String title;
    private String description;
    
    private String priority;
    private LocalDate dueDate;
    private boolean  isCompleted;
    @NotNull
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    
    protected Todo() {}
    @PrePersist
    public void prePersist() {
    	if(id == null) {
    		this.id = UUID.randomUUID();
    	}
    	if(createdAt == null) {
    		this.createdAt = LocalDateTime.now();
    	}

    }

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

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

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

	public void setCompletedAt(LocalDateTime completedAt) {
		this.completedAt = completedAt;
	}
    
}
