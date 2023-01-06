package com.servicecity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Yedukondalu K
 *
 */

@Component
public class PasswordEncoderUtil {

	 @Bean
	 public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder(4);
	    }
}
