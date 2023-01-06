package com.servicecity.service;

/**
 * 
 * @author Yedukondalu K       
 *
 */

public interface GenerateOTPService {

	public String generateOTP(); 
	
	public char[] generateOTP(int length);
}
