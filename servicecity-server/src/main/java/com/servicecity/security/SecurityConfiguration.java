package com.servicecity.security;

import java.util.Collections;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author Yedukondalu K
 *
 */

// @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@Configuration
@EnableWebSecurity
//@Order(2)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoderUtil passwordEncoderUtil;

	@Resource(name = "UserDetailsServiceImpl")
	private UserDetailsService userDetailsService;

	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoderUtil.passwordEncoder());
		System.out.println( auth.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoderUtil.passwordEncoder())+" check");
		System.out.println(passwordEncoderUtil.passwordEncoder().toString()+ " pass");
		//auth.inMemoryAuthentication().withUser("datalabs").password(encoder().encode("password")).roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("SecurityConfig");
		http.csrf().disable()
		//.authorizeRequests().antMatchers("/oauth/token").permitAll()
		//.and()
		.authorizeRequests().antMatchers("/api/user/save").permitAll()
		.and()
		.authorizeRequests().antMatchers("/api/user/phoneNumber/*").permitAll()
		.and()
		.authorizeRequests().antMatchers("/api/user/email/*").permitAll()
		.and()
		.authorizeRequests().anyRequest().authenticated();

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/","/*","/#/login","/assets/img/**","/assets/img/homeMenuIcons/**","/assets/img/inMenusIcons/**","/assets/img/subMenusIcons/**","*.png","/#/","/#/**");
	}

	//@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}


	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		//FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		//bean.setOrder(0);
		//return bean;
		return new CorsFilter(source);
	}


	@Bean public PasswordEncoder encoder() { String idForEncode = "bcrypt";
	BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
	Map<String,PasswordEncoder> encoderMap = Collections.singletonMap(idForEncode, bcrypt);

	DelegatingPasswordEncoder delegating = new
			DelegatingPasswordEncoder(idForEncode, encoderMap);
	delegating.setDefaultPasswordEncoderForMatches(bcrypt); return delegating; }

}
