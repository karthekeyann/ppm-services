package com.cft.hogan.platform.ppm.services.cofig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
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
		.userDnPatterns(env.getProperty("spring.ldap.dn"))
		.groupSearchBase(env.getProperty("spring.ldap.group"))
		.contextSource()
		.url(env.getProperty("spring.ldap.url"))
		.and()
		.passwordCompare()
		.passwordEncoder(new LdapShaPasswordEncoder())
		.passwordAttribute(env.getProperty("spring.ldap.password"));
	}

}
