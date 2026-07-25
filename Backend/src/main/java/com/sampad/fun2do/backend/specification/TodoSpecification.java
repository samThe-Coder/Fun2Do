package com.sampad.fun2do.backend.specification;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import com.sampad.fun2do.backend.entity.Todo;
import com.sampad.fun2do.backend.enums.Priority;

public class TodoSpecification {
	private static final Logger logger = LoggerFactory.getLogger(TodoSpecification.class);
	public TodoSpecification() {}
	
	public static Specification<Todo> belongsToUser(UUID userId){
		
		return (root, query, cb) -> {
				logger.debug("Filtering todos for user: {}", userId);
				return cb.equal(root.get("user").get("id"), userId);
			};
	}
	
	public static Specification<Todo> hasPriority(Priority priority){
		
		return (root, query, cb )-> {
			logger.debug("Filtering todos for priority: {}", priority);
			if(priority == null) {
				return cb.conjunction();
			}
			return cb.equal(root.get("priority"),priority);
		};
	}
	
	public static Specification<Todo> isCompleted(Boolean isCompleted){
		
		return (root, query, cb)-> {
			logger.debug("Filtering todos for completion status: {}", isCompleted);
			if(isCompleted == null) {
				return cb.conjunction();
			}
			return cb.equal(root.get("isCompleted"), isCompleted);
		};
	}

	public static Specification<Todo> containsKeyword(String keyword){
		
		return (root, query, cb) -> {
			logger.debug("Filtering todos for keyword: {}", keyword);
			if(keyword == null || "".equals(keyword)) {
				return cb.conjunction();
			}
			String pattern = String.format("{}%s{}","%", keyword,"%");
			return cb.or(
					cb.like(cb.lower(root.get("title")), pattern),
					cb.like(cb.lower(root.get("description")),pattern)
				);
		};
	}
}
