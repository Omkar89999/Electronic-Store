package com.electronic.store.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.electronic.store.dtos.ApiResponseMessage;

@RestControllerAdvice
public class GlobleExceptionHandler {

	// handler resource not found exception

	private Logger logger = LoggerFactory.getLogger(GlobleExceptionHandler.class);

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {

		logger.info("Exception Handler invoked !!");

		ApiResponseMessage response = ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND)
				.success(true).build();
		return new ResponseEntity<ApiResponseMessage>(response, HttpStatus.NOT_FOUND);
	}

	// method argument not valid exception
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex) {

		List<ObjectError> allError = ex.getBindingResult().getAllErrors();
		Map<String, Object> response = new HashMap<>();
		allError.stream().forEach(objectError -> {
			String message = objectError.getDefaultMessage();
			String field = ((FieldError) objectError).getField();
			response.put(field, message);
		});

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BadApiRequestException.class)
	public ResponseEntity<ApiResponseMessage> handleBadApiRequest(BadApiRequestException ex) {

		logger.info("Bad Api Request !!");

		ApiResponseMessage response = ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND)
				.success(false).build();
		return new ResponseEntity<ApiResponseMessage>(response, HttpStatus.NOT_FOUND);
	}
}
