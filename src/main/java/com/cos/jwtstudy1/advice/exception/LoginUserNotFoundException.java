package com.cos.jwtstudy1.advice.exception;

public class LoginUserNotFoundException extends RuntimeException {

	public LoginUserNotFoundException() {
		super();
	}

	public LoginUserNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public LoginUserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public LoginUserNotFoundException(String message) {
		super(message);
	}

	public LoginUserNotFoundException(Throwable cause) {
		super(cause);
	}

}
