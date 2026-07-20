package com.sampad.fun2do.backend.exception;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	private ErrorResponse buildErrorResponse(HttpStatus status, Object message, HttpServletRequest request) {
		ErrorResponse error = new ErrorResponse();
		error.setTimeStamp(LocalDateTime.now());
		error.setStatus(status.value());
		error.setError(status.getReasonPhrase());
		error.setMessage(message);
		error.setPath(request.getRequestURI());
		
		return error;
	}
		
	private InvalidFormatException findInvalidFormatException(Throwable ex) {
	    Throwable cause = ex;
	    while (cause != null) {
	        if (cause instanceof InvalidFormatException) {
	            return (InvalidFormatException) cause;
	        }
	        cause = cause.getCause();
	    }
	    return null;
	}
	
	//Handle when record not found in database
	@ExceptionHandler(TodoNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleTodoNotFound(TodoNotFoundException ex, HttpServletRequest request){
		logger.warn("Todo not found: {}", ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request));
	}
	
	//Handle when invalid data is passed in request body
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleInvalidRequestBody(HttpMessageNotReadableException ex,HttpServletRequest request){
		String error = "Invalid request body.";
		InvalidFormatException ife = findInvalidFormatException(ex);
		if (ife != null) {
			String fieldName = "Unknown";
		
			if (!ife.getPath().isEmpty()) {
			    fieldName = ife.getPath().get(0).getFieldName();
			}
			
			if (ife.getTargetType() != null && ife.getTargetType().isEnum()) {
			    error = "Invalid value for '" + fieldName + "'. Allowed values are: " + Arrays.toString(ife.getTargetType().getEnumConstants());
			} else {
			    error = "Invalid value for '" + fieldName + "'.";
			}
		}

		logger.warn("Invalid request body received: {}", ex.getMostSpecificCause() != null  ? ex.getMostSpecificCause().getMessage()  : ex.getMessage());

		return ResponseEntity.badRequest().body(buildErrorResponse(HttpStatus.BAD_REQUEST, error, request));
		
	}
	
	//Handle field level validations
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidations(MethodArgumentNotValidException ex, HttpServletRequest request){
		
		Map<String, String> errors = new LinkedHashMap<String, String>();
		ex.getBindingResult().getAllErrors().forEach((err) -> {
			String fieldName = ((FieldError) err).getField();
			String errorMessage = err.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		
		logger.warn("Validation failed: {}", errors);
		return ResponseEntity.badRequest().body(buildErrorResponse(HttpStatus.BAD_REQUEST, errors, request));
	}
	
	//Handle when requested endpoint is not found
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(NoHandlerFoundException ex, HttpServletRequest request){
		logger.warn("Endpoint not found: {}", request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildErrorResponse(HttpStatus.NOT_FOUND, "Endpoint Not Found", request));
	}
	
	//Handle when request is made to an endpoint without required path variable
	@ExceptionHandler(MissingPathVariableException.class)
	public ResponseEntity<ErrorResponse> handleMissingPathVariable(MissingPathVariableException ex, HttpServletRequest request) {
	    logger.warn("Missing path variable: {}", ex.getVariableName());
	    return ResponseEntity.badRequest().body(buildErrorResponse(HttpStatus.BAD_REQUEST, "Missing required path variable: " + ex.getVariableName(), request));
	}
	
	//Handle when request method/s is/are not handled
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
	    String message = "Method " + ex.getMethod() + " is not supported. Supported methods are " + ex.getSupportedHttpMethods();
		logger.warn("Method not allowed: {}", ex.getMethod());
	    return ResponseEntity .status(HttpStatus.METHOD_NOT_ALLOWED).body(buildErrorResponse(HttpStatus.METHOD_NOT_ALLOWED,message,request));
	}
	
	//Handle unexpected parameter values or types 
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {

	    logger.warn("Invalid parameter value: {}", ex.getValue());
	    String message = String.format("Invalid value %s for parameter %s. Expected type %s", ex.getValue() ,ex.getName() ,ex.getRequiredType().getSimpleName());
	    return ResponseEntity.badRequest().body(buildErrorResponse(HttpStatus.BAD_REQUEST,message,request));
	}
	
	//Handle generic exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericExceptions(Exception ex,HttpServletRequest request){
		//need to log the error 
		String error = "An unexpected error occurred!";
		logger.error(error, ex);
		return ResponseEntity.internalServerError().body(buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, error, request));
	}
	
	
}
