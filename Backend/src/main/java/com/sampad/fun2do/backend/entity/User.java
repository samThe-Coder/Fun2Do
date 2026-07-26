package com.sampad.fun2do.backend.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.sampad.fun2do.backend.enums.Role;


@Entity
@Table(name="users")
public class User {
		@Id
		@Column(columnDefinition = "BINARY(16)")
		private UUID id;
		@Column(nullable = false, unique = true)
		private String userName;
		@Column(nullable = false)
		private String password;
		@Enumerated(EnumType.STRING)
		@Column(nullable=false)
		private Role role;
		
		public UUID getId() {
			return id;
		}
		public void setId(UUID id) {
			this.id = id;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public void setRole(Role role) {	
			this.role=role;
		}
		public Role getRole(){
			return role;
		}
		
		@PrePersist
	    public void prePersist() {
	    	if(this.id == null) {
	    		this.id = UUID.randomUUID();
	    	}
	    }
}
