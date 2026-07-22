package com.sampad.fun2do.backend.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sampad.fun2do.backend.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	@Value("${jwt.secret}")
	private  String SECRET;
	
	private Key getKey() {
		return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
	}
	
	public String generateToken(String userName) {
		return Jwts
				.builder()
				.setSubject(userName)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
				.signWith(getKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	//get claims from the token and then store the in the security context's principal so that we can use the same claim to get the 
	//currently logged in user instead of repeatedly querying the db
	public Claims getClaims(String token) {
		return Jwts
					.parserBuilder()
					.setSigningKey(getKey())
					.build()
//					.parseClaimsJwt(token) //for unsigned jwt
					.parseClaimsJws(token)
					.getBody();
	}
	
	public boolean isTokenValid(Claims claims,User user) {

        try {
        	    if(claims.getSubject() == null || claims.getExpiration() == null) {
        	        return false;
        	    }
        	    return claims.getSubject().equals(user.getUserName())  && claims.getExpiration().after(new Date());

        } catch(Exception e) {
            return false;
        }
    }

}
