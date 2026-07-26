package com.sampad.fun2do.backend.dto.user;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sampad.fun2do.backend.enums.Role;

public class UserResponseDTO {
	private UUID id;

    private String userName;

    private Role role;

    private String message;
    
    private String token;
    
    public UserResponseDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @JsonProperty("username")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Role getRole() {
        return role;
    }

    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setRole(Role role) {
        this.role = role;
    }

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
    
}
