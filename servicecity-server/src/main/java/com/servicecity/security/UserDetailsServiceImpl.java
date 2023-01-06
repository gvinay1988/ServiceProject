package com.servicecity.security;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.servicecity.model.User;
import com.servicecity.repository.UserRepository;

/**
 * 
 * @author Yedukondalu K        
 *
 */

@Service(value = "UserDetailsServiceImpl")
public class UserDetailsServiceImpl  implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	//GrantedAuthority ga=null;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> userObj = null;
		if(email != null) {
			userObj = userRepository.findByEmailAndStatus(email, true);
		}
		User user=null;
		if(userObj.isPresent())
			user = userObj.get();
		
		if(user!=null) {
			List<String> roles=null;
			if(!user.getOtpExpiryTime().isAfter(LocalDateTime.now())) {
				roles=user.getRole();
				System.out.println(roles.get(0));
			}
			Set<GrantedAuthority> auths=new HashSet<>();
			if(roles != null) {
				for(String role:roles) {
					GrantedAuthority ga=new SimpleGrantedAuthority("ROLE_"+role);
					auths.add(ga);
					System.out.println(auths);
				}
			}

			return new com.servicecity.model.User(user.getUserId(), user.getPassword(), auths, user.getAccessType(), user.getAddress(),user.getDescription(),
					user.getUserName(),user.getTenantId(),user.getEmail(), user.getPhoneNumber(), user.getRole(), true, true, true, true, true);
		}

		throw new UsernameNotFoundException(email);
	}


	@Bean
	public PasswordEncoder passEncode() {
		String idForEncode = "bcrypt";
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		Map<String, PasswordEncoder> encoderMap = 
				Collections.singletonMap(idForEncode, bcrypt);
		DelegatingPasswordEncoder delegating =
				new DelegatingPasswordEncoder(idForEncode, encoderMap);
		delegating.setDefaultPasswordEncoderForMatches(bcrypt);
		return delegating;
	}
	//@Autowired
	//@Qualifier("bCryptPasswordEncoder")
	//private PasswordEncoder passwordEncoder;

}
