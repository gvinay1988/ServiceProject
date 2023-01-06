package com.servicecity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servicecity.model.Product;
import com.servicecity.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@PostMapping("/saveProduct")
	public ResponseEntity<?> saveProduct(@RequestBody Product product){
		ResponseEntity<?> resp = null;
		try {
			Product productObj = productService.saveProduct(product);
			if(productObj != null) {
				resp = new ResponseEntity<Product>(productObj, HttpStatus.OK);
			}else {
				resp = new ResponseEntity<String>("Please check Product is not saved", HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resp;
		
	}
	
	@GetMapping("/getProduct/id/{id}")
	public ResponseEntity<?> findProduct(@PathVariable(value = "id") String id){
		ResponseEntity<?> resp = null;
		try {
			Product productObj = productService.findProduct(id);
			if(productObj != null) {
				resp = new ResponseEntity<Product>(productObj, HttpStatus.OK);
			}else {
				resp = new ResponseEntity<String>("Please check Product is not available with given id", HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resp;
		
	}
	
	@GetMapping("/getProducts")
	public ResponseEntity<?> findProducts(){
		ResponseEntity<?> resp = null;
		try {
			List<Product> productsObj = productService.findProducts();
			if(productsObj != null && !productsObj.isEmpty() && productsObj.size() > 0) {
				resp = new ResponseEntity<List<Product>>(productsObj, HttpStatus.OK);
			}else {
				resp = new ResponseEntity<String>("Please check Products are not available", HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resp;
		
	}
	
	@DeleteMapping("/deleteProduct/id/{id")
	public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") String id){
		ResponseEntity<?> resp = null;
		try {
			Product productObj = productService.deleteProduct(id);
			if(productObj != null) {
				resp = new ResponseEntity<Product>(productObj, HttpStatus.OK);
			}else {
				resp = new ResponseEntity<String>("Please check Product is not delete with given id", HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resp;
		
	}
	
	@PostMapping("/updateProduct/id/{id}")
	public ResponseEntity<?> saveProduct(@PathVariable(value = "id") String id, @RequestBody Product product){
		ResponseEntity<?> resp = null;
		try {
			Product productObj = productService.updateProduct(id, product);
			if(productObj != null) {
				resp = new ResponseEntity<Product>(productObj, HttpStatus.OK);
			}else {
				resp = new ResponseEntity<String>("Please check Product is not saved", HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resp;
		
	}
}
