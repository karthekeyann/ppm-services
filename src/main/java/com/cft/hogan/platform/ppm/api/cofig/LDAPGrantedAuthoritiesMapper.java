package com.cft.hogan.platform.ppm.api.cofig;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;

public class LDAPGrantedAuthoritiesMapper implements GrantedAuthoritiesMapper {

	@Override
	public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
		return authorities;
	}
}


