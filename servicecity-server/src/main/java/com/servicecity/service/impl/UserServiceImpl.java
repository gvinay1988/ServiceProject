package com.servicecity.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.servicecity.model.User;
import com.servicecity.repository.UserRepository;
import com.servicecity.security.PasswordEncoderUtil;
import com.servicecity.service.UserService;

/**
 * 
 * @author Yedukondalu K        
 *
 */

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger logger=LogManager.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	public PasswordEncoderUtil passwordEncoderutil;
	
	@Transactional
	public User create(User dto) {
		logger.info(">>start of User create method");
		System.out.println(dto.getPassword());
		dto.setPassword(passwordEncoderutil.passwordEncoder().encode(dto.getPassword()));
		return userRepository.save(dto);
	}


	@Transactional
	public User delete(String id) {
		logger.info(">>start of User delete method");
		User user=new User();
		Optional<User> userOptional=userRepository.findById(id);
		if(userOptional.isPresent()) {
			user=userOptional.get();			
			userRepository.delete(user);
		}	
		return user;
	}

	@Transactional(readOnly = true)
	public User findById(String id) {
		logger.info(">>start of User findById method");
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			return user.get();
		}
       return null;
	}
	

	@Transactional(readOnly = true)
	public List<User> findAll() {
		logger.info(">>start of User findAll method");
        List<User> found = userRepository.findAll();
        return found;
	}

	@Transactional(readOnly = true)
	public Optional<User> findUserByPhoneNumber(Long phoneNumber) {
		logger.info(">>start of User findUserByPhoneNumber method");
		return userRepository.findByPhoneNumberAndStatus(phoneNumber, true);
	}

	@Transactional(readOnly = true)
	public Optional<User> findUserByEmail(String email) {
		logger.info(">>start of User findUserByEmail method");
		return userRepository.findByEmailAndStatus(email, true);
	}

}
