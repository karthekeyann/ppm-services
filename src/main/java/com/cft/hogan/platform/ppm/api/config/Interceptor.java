package com.cft.hogan.platform.ppm.api.config;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.cft.hogan.platform.ppm.api.config.context.ApplicationContext;
import com.cft.hogan.platform.ppm.api.exception.Forbidden;

@Component
public class Interceptor implements HandlerInterceptor {

	@Autowired
	Environment env;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

		/*
		 * Role based Authorization check
		 */
		authorizationCheck();
		return true;
	}

	private boolean authorizationCheck() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		/*
		 * skip the authorization when user is not authenticated. 
		 */
		if(auth.getName() == null || "anonymousUser".equalsIgnoreCase(auth.getName())){
			return true;
		}
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		for(GrantedAuthority authority : authorities){
			if(env.getProperty("spring.ldap.role.user").equalsIgnoreCase(authority.getAuthority()) 
					|| env.getProperty("spring.ldap.role.admin").equalsIgnoreCase(authority.getAuthority())) {
				return true;
			}
		}
		//clear session and throw forbidden error if user fail authorization check 
		ApplicationContext.logout();
		throw new Forbidden("User not authorized to access PPM application.");
	}
}