package com.cft.hogan.platform.ppm.services.massmaintenance.exception;

public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public boolean processFurther = false;

	public BusinessException(String message, boolean processFurther) {
		super(message);
		this.processFurther = processFurther;
	}
}
