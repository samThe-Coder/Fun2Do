package com.sampad.fun2do.backend.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sampad.fun2do.backend.entity.User;
import com.sampad.fun2do.backend.repository.UserRepository;
import com.sampad.fun2do.backend.security.UserPrincipal;
import com.sampad.fun2do.backend.service.JwtService;

import io.jsonwebtoken.Claims;
@Component
public class JwtFilter extends OncePerRequestFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
	private final JwtService jwtService;
	private final UserRepository userRepo;
	
	public JwtFilter (JwtService jwtService, UserRepository userRepo) {
		this.jwtService=jwtService;
		this.userRepo = userRepo;
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String header = request.getHeader("Authorization");
		
		if(header == null || !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		try {
			String token = header.substring(7);
			Claims claims = jwtService.getClaims(token);
			String userName = claims.getSubject();
			
			User user = userRepo.findByUserName(userName).orElse(null);
			
	//		List<SimpleGrantedAuthority> authorities =new ArrayList<SimpleGrantedAuthority>();
			
			if(user != null && jwtService.isTokenValid(claims, user) && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserPrincipal principal =
				        new UserPrincipal(
				            user.getId(),
				            user.getUserName(),
				            user.getRole()
				        );
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principal,null,principal.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}catch(Exception ex) {
			logger.warn("Invalid JWT token received ");
			SecurityContextHolder.clearContext();
		}
		filterChain.doFilter(request, response);
	}

}
