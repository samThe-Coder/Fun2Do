package com.sampad.fun2do.backend.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sampad.fun2do.backend.dto.user.LoginUserDTO;
import com.sampad.fun2do.backend.dto.user.RegisterUserDTO;
import com.sampad.fun2do.backend.dto.user.UserResponseDTO;
import com.sampad.fun2do.backend.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	private final UserService userServ;
	public AuthController(UserService userServ) {
		this.userServ=userServ;
	}
	@PostMapping("/signup")
	public ResponseEntity<UserResponseDTO> signUp(@Valid @RequestBody RegisterUserDTO request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.userServ.signUp(request));
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserResponseDTO> logIn(@Valid @RequestBody LoginUserDTO request) {
		return ResponseEntity.ok(this.userServ.logIn(request));
	}
}
