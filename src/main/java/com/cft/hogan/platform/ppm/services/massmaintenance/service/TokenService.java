package com.cft.hogan.platform.ppm.services.massmaintenance.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cft.hogan.platform.ppm.services.massmaintenance.util.Constants;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.Utils;

@Service
public class TokenService {

	public String getUser() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	public String getToken() {
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession(false);
		if(session!=null) {
			return session.getId();
		}
		return Constants.EMPTY;
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

