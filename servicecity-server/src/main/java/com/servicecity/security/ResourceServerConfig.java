package com.servicecity.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author Yedukondalu K
 *
 */

@EnableResourceServer
@Configuration
//@Order(2)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
	
	private static final String RESOURCE_ID = "cityservices";
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(RESOURCE_ID).stateless(false);
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		System.out.println("resource server");
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.POST,"/api/user/save").permitAll()
		.antMatchers(HttpMethod.GET,"/api/user/phoneNumber/*").permitAll()
		.antMatchers(HttpMethod.GET,"/api/user/email/*").permitAll()
		.anyRequest().authenticated()
		.and().cors().and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Order(Ordered.HIGHEST_PRECEDENCE)
	@Component
	public class corsFilter implements Filter {

	    private final Logger log = LoggerFactory.getLogger(ResourceServerConfig.class);

	    public corsFilter() {
	        log.info("****************** SimpleCORSFilter cons ******************");
	    }

	    @Override
	    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
	        log.info("****************** SimpleCORSFilter doFilter ******************");

	        HttpServletRequest request = (HttpServletRequest) req;
	        HttpServletResponse response = (HttpServletResponse) res;

	        response.setHeader("Access-Control-Allow-Origin", "*");
	        response.setHeader("Access-Control-Allow-Credentials", "true");
	        response.setHeader("Access-Control-Allow-Methods", "POST, PUT , GET, OPTIONS, DELETE");
	        response.setHeader("Access-Control-Max-Age", "3600");
	        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept, X-Requested-With, remember-me");

	        chain.doFilter(req, res);
	    }

	    @Override
	    public void init(FilterConfig filterConfig) {
	        log.info("****************** SimpleCORSFilter init ******************");
	    }

	    @Override
	    public void destroy() {
	        log.info("****************** SimpleCORSFilter destroy ******************");
	    }


	}
	
}
