package com.cft.hogan.platform.ppm.services.massmaintenance.exception;

public class ItemNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ItemNotFoundException() {
		super("Item Not Found");
	}
}
