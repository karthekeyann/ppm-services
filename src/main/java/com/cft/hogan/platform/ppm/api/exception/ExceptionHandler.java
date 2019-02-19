package com.cft.hogan.platform.ppm.api.exception;

import java.io.IOException;

import javax.persistence.NoResultException;

import org.springframework.dao.EmptyResultDataAccessException;

import com.cft.hogan.platform.ppm.api.config.context.ApplicationContext;
import com.cft.hogan.platform.ppm.api.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionHandler {

	public static void handleException(Exception e) {
		try {
			log.error(Utils.getLogMsg());
			log.error("Service Endpoint:"+ApplicationContext.getEndPoint());
			log.error(e.getMessage(),e );
			if(e instanceof BusinessError) {
				if(((BusinessError) e).processFurther) {
					log.error("Business error occured. Error information logged and continue processing.");
				}else{
					throw new BadRequest(e.getMessage());
				}
			}else if(e instanceof ItemNotFound || e instanceof NoResultException || e instanceof EmptyResultDataAccessException) {
				throw new ItemNotFound();
			}else if(e instanceof BadRequest) {
				throw new BadRequest(e.getMessage());
			}else if(e instanceof Forbidden) {
				throw new Forbidden(e.getMessage());
			}else if(e instanceof SystemError) {
				throw new SystemError(e.getMessage());
			}else {
				throw new SystemError(e.getMessage());
			}
		} catch (IOException ex) {
			handleException(ex);
		}
	}

}
