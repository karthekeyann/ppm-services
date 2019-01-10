package com.cft.hogan.platform.ppm.api.facade.mm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cft.hogan.platform.ppm.api.util.Utils;

@Service
public class TokenFacade {

	public String getUser() {
		return Utils.getLoggedInUser();
	}

	public String getToken() {
		return Utils.getSessionID();
	}

	public void logout() {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}catch(Exception e) {
			Utils.handleException(e);
		}
	}
}
