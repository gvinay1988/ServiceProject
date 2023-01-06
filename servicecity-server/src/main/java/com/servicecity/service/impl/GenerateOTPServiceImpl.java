package com.servicecity.service.impl;

import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.servicecity.model.GenerateOTP;
import com.servicecity.service.GenerateOTPService;

/**
 * 
 * @author Yedukondalu K       
 *
 */

@Service
public class GenerateOTPServiceImpl implements GenerateOTPService {

	@Transactional
	public String generateOTP() {
		GenerateOTP generateOTP = new GenerateOTP();
		int randomPin = (int) (Math.random()*9000) + 1000;
		String otp = String.valueOf(randomPin);
		generateOTP.setOtp(otp);
		return generateOTP.getOtp();
	}

	@Transactional
	public char[] generateOTP(int length) {
		String numbers = "1234567890";
		Random random = new Random();
		char[] otp = new char[4];
		for(int i=0; i <length; i++) {
			otp[i] = numbers.charAt(random.nextInt(numbers.length()));
		}
		
		return otp;
	}

}
