package com.sampad.fun2do.backend.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Value("${app.frontend.url}")
	private  String FRONTEND_URL;
	private  final JwtFilter jwtFilter;
	
	public SecurityConfig(JwtFilter jwtFilter) {
		this.jwtFilter=jwtFilter;
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList(FRONTEND_URL));
		configuration.setAllowedMethods(Arrays.asList("GET","POST","PATCH","DELETE","OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	@Bean
	 SecurityFilterChain filterChain(HttpSecurity http) throws Exception{ //public
		http
		.cors()
//		.configurationSource(corsConfigurationSource())
		.and()
        .csrf().disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
//        .antMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll() //to allow cors browser preflight
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
