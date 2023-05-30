package com.colin.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsupportedMathOperationException extends RuntimeException{

	public UnsupportedMathOperationException(String string) {
		super(string);
	}

	private static final long serialVersionUID = 1L;
	

}
