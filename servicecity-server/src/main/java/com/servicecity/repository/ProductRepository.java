package com.servicecity.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.servicecity.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

}
