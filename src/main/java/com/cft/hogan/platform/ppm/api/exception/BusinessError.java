package com.cft.hogan.platform.ppm.api.exception;

public class BusinessError extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public boolean processFurther = false;

	public BusinessError(String message, boolean processFurther) {
		super(message);
		this.processFurther = processFurther;
	}
}
