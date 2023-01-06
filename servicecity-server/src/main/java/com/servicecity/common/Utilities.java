package com.servicecity.common;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.servicecity.dto.UserInfo;
import com.servicecity.model.User;
import com.servicecity.repository.UserRepository;

/**
 * 
 * @author Yedukondalu K
 *
 */

@Service
public class Utilities {

	@Autowired
	private UserRepository userRepository;
	
	public UserInfo getUserData() {
		UserInfo userInfo= null;
		String loggedInUserEmailId=null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			loggedInUserEmailId = ((UserDetails)principal).getUsername();
			Optional<User> user=userRepository.findByEmailAndStatus(loggedInUserEmailId, true);
			if(user.isPresent()) {
				userInfo= new UserInfo();
				userInfo.setEmail(user.get().getEmail());
				userInfo.setTenantId(user.get().getTenantId());
				userInfo.setRoles(user.get().getRole());
			}
		}
		return userInfo;
	}

}
