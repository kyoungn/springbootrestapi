package com.cos.jwtstudy1.advice.exception;

public class AuthenticationEntryPointException extends RuntimeException {

	public AuthenticationEntryPointException() {
		super();
	}

	public AuthenticationEntryPointException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AuthenticationEntryPointException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthenticationEntryPointException(String message) {
		super(message);
	}

	public AuthenticationEntryPointException(Throwable cause) {
		super(cause);
	}

}
