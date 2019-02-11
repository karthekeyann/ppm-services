package com.cft.hogan.platform.ppm.api.exception;

public class SystemError extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SystemError() {
		super("System error occurred. Please try again or contact system administrator.");
	}
	
	
	public SystemError(String message) {
		super(message);
	}

}
