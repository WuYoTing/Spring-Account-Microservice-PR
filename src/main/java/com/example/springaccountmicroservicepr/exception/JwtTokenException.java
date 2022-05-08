package com.example.springaccountmicroservicepr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class JwtTokenException extends RuntimeException {

	private static final long serialVersionUID = 3968014551475447788L;

	public JwtTokenException(String message) {
		super(String.format("Failed for : %s", message));
	}
}
