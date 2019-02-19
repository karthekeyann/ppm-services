package com.cft.hogan.platform.ppm.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
		.userSearchFilter(env.getProperty("spring.ldap.userSearchFilter"))
		.contextSource(getContextSource())
		.ldapAuthoritiesPopulator(getLdapAuthoritiesPopulator());
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

}
