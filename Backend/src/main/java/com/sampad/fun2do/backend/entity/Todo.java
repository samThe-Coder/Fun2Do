package com.sampad.fun2do.backend.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sampad.fun2do.backend.enums.Priority;

@Entity
@Table(name="todo")
public class Todo {
	@Id
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;
	
	@Column(nullable=false)
    private String title;
	
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Priority priority=Priority.MEDIUM;
    
    private LocalDate dueDate;
    
    @Column(columnDefinition = "BIT(1) DEFAULT 0")
    private boolean  isCompleted;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime completedAt;
    
    @Transient
    public long getDaysRemaining() {
        if (dueDate == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(LocalDate.now(), dueDate);
    }
    
    public Todo() {}
    
    @PrePersist
    public void prePersist() {
    	if(this.id == null) {
    		this.id = UUID.randomUUID();
    	}
    	if(this.createdAt == null) {
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

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(boolean isCompleted) {
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
