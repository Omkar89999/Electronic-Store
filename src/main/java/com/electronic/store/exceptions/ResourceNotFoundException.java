package com.electronic.store.exceptions;

import lombok.Builder;

//@RestControllerAdvice
@Builder
public class ResourceNotFoundException extends RuntimeException{

	public ResourceNotFoundException() {
		super("Resource not found !!");
	}
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
	
}
