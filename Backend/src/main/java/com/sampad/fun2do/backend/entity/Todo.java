package com.sampad.fun2do.backend.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name="TODO")
public class Todo {
	@Id
	private UUID id;
    private String title;
    private String description;
    private String priority;
    private LocalDate dueDate;
    boolean isCompleted;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    
    @PrePersist
    public void generateID() {
    	if(id == null) {
    		this.id = UUID.randomUUID();
    	}

    }
    
}
