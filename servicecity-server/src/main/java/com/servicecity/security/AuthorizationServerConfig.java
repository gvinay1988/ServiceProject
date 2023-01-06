package com.servicecity.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * @author Yedukondalu K
 *
 */

@EnableAuthorizationServer
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

	//@Autowired
	//private TokenStore tokenStore;

	@Autowired
	private PasswordEncoderUtil passwordEncoderUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;


	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

		//security.allowFormAuthenticationForClients();
		security
		.tokenKeyAccess("permitAll()")
		.checkTokenAccess("isAuthenticated()")
		.allowFormAuthenticationForClients()
		.passwordEncoder(passwordEncoderUtil.passwordEncoder());//check later
	}


	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		System.out.println("********************"+"Entered");
		// BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		clients.inMemory()
		.withClient("client")
		.secret(passwordEncoderUtil.passwordEncoder().encode("secret"))
		.authorizedGrantTypes("password", "authorization_code", "refresh_token","implicit","client_credentials")
		.scopes("read","write","trust")
		.accessTokenValiditySeconds(7000)
		.refreshTokenValiditySeconds(10000);
		System.out.println(passwordEncoderUtil.passwordEncoder().encode("secret"));
		System.out.println("********************"+"Exit"
				+ "");

	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer()));

		endpoints.tokenStore(tokenStore())
		.tokenEnhancer(tokenEnhancerChain)
		.authenticationManager(authenticationManager)
		.userDetailsService(userDetailsService)
		.exceptionTranslator(loggingExceptionTranslator());
	}

	@Bean
	public WebResponseExceptionTranslator loggingExceptionTranslator() {
		return new DefaultWebResponseExceptionTranslator() {
			@Override
			public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
				// This is the line that prints the stack trace to the log. You can customise this to format the trace etc if you like
				e.printStackTrace();

				// Carry on handling the exception
				ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
				HttpHeaders headers = new HttpHeaders();
				headers.setAll(responseEntity.getHeaders().toSingleValueMap());
				OAuth2Exception excBody = responseEntity.getBody();
				return new ResponseEntity<>(excBody, headers, responseEntity.getStatusCode());
			}
		};
	}


	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();

	}

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer() ;
	}

}

