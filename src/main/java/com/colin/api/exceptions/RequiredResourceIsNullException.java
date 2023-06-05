package com.colin.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredResourceIsNullException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	

	public RequiredResourceIsNullException(String string) {
		super(string);
	}
	
	public RequiredResourceIsNullException() {
		super("It is not allowed to persit a null object");
	}



}
