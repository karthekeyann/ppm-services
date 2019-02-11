package com.cft.hogan.platform.ppm.api.facade;

import org.springframework.stereotype.Service;

import com.cft.hogan.platform.ppm.api.config.context.ApplicationContext;

@Service
public class TokenFacade {

	public String getUser() {
		return ApplicationContext.getLoggedInUser();
	}

	public String getToken() {
		return ApplicationContext.getSessionID();
	}

	public void delete() {
		ApplicationContext.logout();
	}
}

