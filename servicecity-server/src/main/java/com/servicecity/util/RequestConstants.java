package com.servicecity.util;

//import org.springframework.security.core.userdetails.UserDetails;

/**
 * 
 * @author Yedukondalu K       
 *
 */

public class RequestConstants {

	public interface Anonymous {
		public static final String ANONYMOUS_USER = "anonymousUser";
	}

	public interface Roles {
		public static final String ROLE_ADMIN = "ADMIN";
		public static final String ROLE_USER = "USER";
		public static final String ROLE_SUPER_ADMIN = "SUPER_ADMIN";
	}
	
//	public static UserDetails getLogInUserDetails(String source){
//		 
//		return null;		
//	}
//	

}
