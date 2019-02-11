package com.cft.hogan.platform.ppm.api.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

	@ResponseBody
	@ExceptionHandler(ItemNotFound.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String itemNotFoundHandler(ItemNotFound ex) {
		return ex.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(BadRequest.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String badRequestHandler(BadRequest ex) {
		return ex.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(SystemError.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	String systemErrorHandler(SystemError ex) {
		return ex.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	String exceptionHandler(Exception ex) {
		return ex.getMessage();
	}


	@ResponseBody
	@ExceptionHandler(Forbidden.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	String ForbiddenExceptionHandler(Forbidden ex) {
		return ex.getMessage();
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<Object>(ex.getBindingResult().toString(), HttpStatus.BAD_REQUEST);
	} 
}
