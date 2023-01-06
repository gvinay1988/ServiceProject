package com.servicecity.service;

import java.util.List;

import com.servicecity.model.Product;

public interface ProductService {
	
	Product saveProduct(Product product);
	Product updateProduct(String id, Product product);
	Product findProduct(String id);
	List<Product> findProducts();
	Product deleteProduct(String id);
 
}
