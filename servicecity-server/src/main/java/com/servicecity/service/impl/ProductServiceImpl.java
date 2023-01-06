package com.servicecity.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.servicecity.model.Product;
import com.servicecity.repository.ProductRepository;
import com.servicecity.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	
	@Autowired
	private ProductRepository productRepository;

	@Transactional
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

	@Transactional
	public Product updateProduct(String id, Product product) {
		if(id != null) {
			if(product.getId().equals("id")) {
				return productRepository.save(product);
			}
		}
		return null;
	}

	@Transactional(readOnly = true)
	public Product findProduct(String id) {
		Optional<Product> product = productRepository.findById(id);
		if(product != null) {
			return product.get();
		}
		return null;
	}

	@Transactional(readOnly = true)
	public List<Product> findProducts() {
		return productRepository.findAll();
	}

	@Transactional
	public Product deleteProduct(String id) {
		Optional<Product> product = productRepository.findById(id);
		if(product.isPresent()) {
			productRepository.delete(product.get());
			return product.get();
		}
		return null;
	}

}
