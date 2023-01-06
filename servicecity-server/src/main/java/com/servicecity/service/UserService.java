package com.servicecity.service;

import java.util.List;
import java.util.Optional;

import com.servicecity.model.User;

/**
 * 
 * @author Yedukondalu K        
 *
 */

public interface UserService {

	public User create(User dto);

	public User delete(String id);

	public User findById(String id);

	public List<User> findAll();
    
	public Optional<User> findUserByPhoneNumber(Long phoneNumber);
	
	public Optional<User> findUserByEmail(String email);
    
}
