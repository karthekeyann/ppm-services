package com.cft.hogan.platform.ppm.services.massmaintenance.exception;

import com.cft.hogan.platform.ppm.services.context.SystemContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessException(String message) {
		super(message);
		log.error("Business error occured. Error information logged and continue processing.");
		log.error(message);
	}
	
	
	public BusinessException(Exception e) {
		super(e);
		SystemContext.logDetails();
	}
}
