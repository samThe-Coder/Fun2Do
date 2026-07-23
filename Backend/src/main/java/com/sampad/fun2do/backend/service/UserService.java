package com.sampad.fun2do.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sampad.fun2do.backend.dto.user.LoginUserDTO;
import com.sampad.fun2do.backend.dto.user.RegisterUserDTO;
import com.sampad.fun2do.backend.dto.user.UserResponseDTO;
import com.sampad.fun2do.backend.entity.User;
import com.sampad.fun2do.backend.enums.Role;
import com.sampad.fun2do.backend.exception.user.InvalidCredentialsException;
import com.sampad.fun2do.backend.exception.user.UserAlreadyExistsException;
import com.sampad.fun2do.backend.exception.user.UserNotFoundException;
import com.sampad.fun2do.backend.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepo;
	private final PasswordEncoder encoder;
	private final JwtService jwtService;
	
	public UserService(UserRepository userRepo, PasswordEncoder encoder,JwtService jwtServ) {
		this.userRepo=userRepo;
		this.encoder=encoder;
		this.jwtService=jwtServ;
	}
	
	private UserResponseDTO mapToUserResponseDTO(User user, String message, String token) {
		UserResponseDTO dto = new UserResponseDTO();
		dto.setId(user.getId());
		dto.setUserName(user.getUserName());
		dto.setRole(user.getRole());
		dto.setMessage(message);
		dto.setToken(token);
		return dto;
	}
	public UserResponseDTO signUp(RegisterUserDTO dto) {
		if(this.userRepo.findByUserName(dto.getUserName()).isPresent()) {
			throw new UserAlreadyExistsException("User Already Exists!");
		}
		
		User user = new User();
		user.setUserName(dto.getUserName());
		user.setPassword(this.encoder.encode(dto.getPassword()));
		user.setRole(Role.USER);
		user = 	this.userRepo.save(user);
		
		String token = jwtService.generateToken(user.getUserName());
		return mapToUserResponseDTO(user, "User Creation Was Successful!",token);
				
	}
	
	public UserResponseDTO logIn(LoginUserDTO dto) {
		User existingUser = userRepo.findByUserName(dto.getUserName()).orElseThrow(()->new UserNotFoundException("User Not Found"));
		
		if(!this.encoder.matches(dto.getPassword(),existingUser.getPassword())) {
			throw new InvalidCredentialsException("The Credentials Does Not Match!");
		}
		
		String token = jwtService.generateToken(existingUser.getUserName());
		return mapToUserResponseDTO(existingUser, "Login Successful", token);
	}
	
	
}
