package com.atguigu.ems.web.exceptions;

@SuppressWarnings("serial")
public class AccountLockedException extends RuntimeException {

	public AccountLockedException() {
		super();
	}

	public AccountLockedException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AccountLockedException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountLockedException(String message) {
		super(message);
	}

	public AccountLockedException(Throwable cause) {
		super(cause);
	}
	
}
