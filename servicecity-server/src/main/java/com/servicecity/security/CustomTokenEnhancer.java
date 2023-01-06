package com.servicecity.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.servicecity.model.User;

/**
 * @author Yedukondalu K
 *
 */

public class CustomTokenEnhancer implements TokenEnhancer{

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication){
		final Map<String, Object> info = new HashMap<>();
		User c=(User) authentication.getPrincipal();
		if(c.getAccessType().equalsIgnoreCase("Write"))
			c.setAccessType("True");
		else
			c.setAccessType("False");
		c.setPassword(null);
		//c.setOrganisationId(null);
		//c.setOrganisationName(null);
		//c.setResetToken(null);
		c.setGrantedAuthorities(null);

		info.put("user",c);
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}

}
