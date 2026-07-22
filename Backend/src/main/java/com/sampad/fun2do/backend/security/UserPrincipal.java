package com.sampad.fun2do.backend.security;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sampad.fun2do.backend.enums.Role;

public class UserPrincipal implements UserDetails{
	private final UUID id;
	private final String userName;
	private final Role role;
	public UserPrincipal(UUID id, String userName, Role role) {
		this.id=id;
		this.userName=userName;
		this.role=role;
	}
	public UUID getId() {
		return id;
	}
	
	public Role getRole() {
		return role;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(
		        new SimpleGrantedAuthority("ROLE_" + role.name())
		    );
	}
	@Override
	public String getPassword() {
		return null;
	}
	@Override
	public String getUsername() {
		return userName;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
}
