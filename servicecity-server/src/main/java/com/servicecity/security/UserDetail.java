package com.servicecity.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Yedukondalu K
 *
 */
public class UserDetail implements UserDetails {
	
	private String password;
	private final String userName;
	private final List<GrantedAuthority> authorities;
	private final boolean accountNonExpired;
	private final boolean accountNonLocked;
	private final boolean credentialsNonExpired;
	private final boolean enabled ;
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	public UserDetail(String userName, String password, List<GrantedAuthority> authorities) {
		//super();
		System.out.println("*********Entered into UserDetail");
		this.password = password;
		this.userName = userName;
		this.authorities = authorities;
		this.accountNonExpired=true;
		this.accountNonLocked=true;
		this.credentialsNonExpired=true;
		this.enabled=true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		System.out.println("*********Entered into UserDeail getAuthorities method");
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}
/*
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		String pwd=encoder().encode("password");//  passwordEncoder.encode("password");
		return pwd;
	}
*/
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}
	
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
