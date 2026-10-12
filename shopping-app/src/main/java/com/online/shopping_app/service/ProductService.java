package com.online.shopping_app.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.online.shopping_app.exception.ProductNotFoundException;
import com.online.shopping_app.model.Product;
import com.online.shopping_app.repository.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Product addProduct(Product product) {
		return productRepository.save(product);
	}

	public Product findProductById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException(id));
	}

	public Product updateProductById(Long id, Product newProduct) {
		Product existingDetails = productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException(id));
		existingDetails.setName(newProduct.getName());
		existingDetails.setDescription(newProduct.getDescription());
		existingDetails.setPrice(newProduct.getPrice());
		existingDetails.setStock(newProduct.getStock());
		return productRepository.save(existingDetails);
	}

	public void deleteProductById(Long id) {
		productRepository.deleteById(id);
	}

}