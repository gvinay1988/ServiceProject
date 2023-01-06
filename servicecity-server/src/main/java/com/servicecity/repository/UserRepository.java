package com.servicecity.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.servicecity.model.User;

/**
 * 
 * @author Yedukondalu K        
 *
 */

public interface UserRepository extends MongoRepository<User, String> {

	public Optional<User> findByPhoneNumberAndStatus(Long phoneNumber, Boolean status);

	public Optional<User> findByEmailAndStatus(String email, Boolean status);

}
