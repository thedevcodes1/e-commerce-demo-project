package com.online.shopping_app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.online.shopping_app.dto.ProductPatchRequest;
import com.online.shopping_app.dto.ProductRequest;
import com.online.shopping_app.dto.ProductResponse;
import com.online.shopping_app.exception.ProductNotFoundException;
import com.online.shopping_app.model.Product;
import com.online.shopping_app.repository.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public Page<ProductResponse> getAllProducts(Pageable pageable) {
		return productRepository.findAll(pageable).map(this::response);
	}

	public ProductResponse addProduct(ProductRequest request) {
		Product product = new Product();
		saveProductDetails(request, product);
		return response(productRepository.save(product));
	}

	public ProductResponse findProductById(Long id) {
		return response(findEntity(id));
	}

	public ProductResponse updateProduct(Long id, ProductRequest request) {
		Product product = findEntity(id);
		saveProductDetails(request, product);
		return response(productRepository.save(product));	
	}
	
	public ProductResponse partialUpdateProduct(Long id, ProductPatchRequest request) {
	    Product product = findEntity(id);
	    if (request.name() != null) product.setName(request.name());
	    if (request.description() != null) product.setDescription(request.description());
	    if (request.price() != null) product.setPrice(request.price());
	    if (request.stock() != null) product.setStock(request.stock());
	    return response(productRepository.save(product));
	}

	public void deleteProduct(Long id) {
		findEntity(id);
		productRepository.deleteById(id);
	}
	
	private Product findEntity(Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException(id));
	}
	
	private ProductResponse response(Product product) {
		return new ProductResponse(
				product.getId(),
				product.getName(),
				product.getDescription(),
				product.getPrice(),
				product.getStock()
		);				
	}
	
	private void saveProductDetails(ProductRequest request, Product product) {
		product.setName(request.name());
		product.setDescription(request.description());
		product.setPrice(request.price());
		product.setStock(request.stock());
		
	}
}