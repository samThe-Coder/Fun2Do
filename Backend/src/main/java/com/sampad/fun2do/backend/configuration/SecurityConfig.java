package com.sampad.fun2do.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private  final JwtFilter jwtFilter;
	
	public SecurityConfig(JwtFilter jwtFilter) {
		this.jwtFilter=jwtFilter;
	}
	
	@Bean
	 SecurityFilterChain filterChain(HttpSecurity http) throws Exception{ //public
		http
        .csrf().disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/api/v1/auth/**").permitAll()
        .antMatchers("/api/v1/**").hasAnyRole("USER","ADMIN") 
        .anyRequest().authenticated();

    http.addFilterBefore(
            jwtFilter,
            UsernamePasswordAuthenticationFilter.class
    );
//		http.csrf(csrf-> csrf.disable())
//		.authorizeHttpRequests(auth -> auth.authorizeRequests()
//				.antMatchers("/auth/**").permitAll().anyRequest().authenticated()).addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	@Bean
	 PasswordEncoder passwordEncoder() { //public
		return new BCryptPasswordEncoder();
	}
}
