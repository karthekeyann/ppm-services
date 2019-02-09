package com.cft.hogan.platform.ppm.api.cofig;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cft.hogan.platform.ppm.api.exception.ForbiddenException;
import com.cft.hogan.platform.ppm.api.util.Utils;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	Environment env;

	@Override
	protected void configure(HttpSecurity http) throws Exception { 
		http
		.httpBasic()
		.and()
		.csrf().disable()
		.cors()
		.and()
		.authorizeRequests().anyRequest().authenticated();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.ldapAuthentication()
		.userDnPatterns(env.getProperty("spring.ldap.dn"))	//ex: uid={0},ou=people
		.contextSource(getContextSource())
		.ldapAuthoritiesPopulator(getLdapAuthoritiesPopulator());
		/*
		 * Role based Authorization check
		 */
		authorizationCheck();
	}

	@Bean
	public LdapContextSource getContextSource() {
		LdapContextSource contextSource = new DefaultSpringSecurityContextSource(env.getProperty("spring.ldap.url"));
		contextSource.setUserDn(env.getProperty("spring.ldap.mDn"));
		contextSource.setPassword(env.getProperty("spring.ldap.mPassword"));
		return contextSource;
	}


	@Bean
	public LdapAuthoritiesPopulator getLdapAuthoritiesPopulator() {
		DefaultLdapAuthoritiesPopulator ldapAuthoritiesPopulator =
				new DefaultLdapAuthoritiesPopulator(getContextSource(), env.getProperty("spring.ldap.group.search.base"));
		ldapAuthoritiesPopulator.setGroupRoleAttribute(env.getProperty("spring.ldap.group.role.att"));
		ldapAuthoritiesPopulator.setSearchSubtree(true);
		return ldapAuthoritiesPopulator;
	}

	private boolean authorizationCheck() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		for(GrantedAuthority authority : authorities){
			if(env.getProperty("spring.ldap.role.user").equalsIgnoreCase(authority.getAuthority()) 
					|| env.getProperty("spring.ldap.role.admin").equalsIgnoreCase(authority.getAuthority())) {
				return true;
			}
		}
		//logout, clear session and throw forbidden error if user fail authorization check 
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		new SecurityContextLogoutHandler().logout(request, response, auth);
		Utils.getSession().invalidate();
		throw new ForbiddenException("User not authorized to access PPM application.");
	}

}
