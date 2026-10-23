package com.online.shopping_app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public Page<ProductResponse> getAllProducts(Pageable pageable) {
		
		logger.info("Fetching products page: pageNumber={}, pageSize={}, sort={}", pageable.getPageNumber(),
				pageable.getPageSize(), pageable.getSort());
		
		Page<ProductResponse> products = productRepository.findAll(pageable).map(this::response);
		
		logger.info("Successfully retrieved {} products (Total elements: {})", products.getNumberOfElements(),
				products.getTotalElements());
		
		return products;
	}

	public ProductResponse addProduct(ProductRequest request) {
		Product product = new Product();
		saveProductDetails(request, product);
		ProductResponse productResponse = response(productRepository.save(product));
		logger.info("New product added with id : {}", productResponse.id());
		return productResponse;
	}

	public ProductResponse findProductById(Long id) {
		ProductResponse productResponse = response(findEntity(id));
		logger.info("Retrieved product with id : {}", productResponse.id());
		return productResponse;
	}

	public ProductResponse updateProduct(Long id, ProductRequest request) {
		Product product = findEntity(id);
		saveProductDetails(request, product);
		ProductResponse productResponse = response(productRepository.save(product));
		logger.info("Product updated with id : {}", productResponse.id());
		return productResponse;
	}

	public ProductResponse partialUpdateProduct(Long id, ProductPatchRequest request) {
		Product product = findEntity(id);
		if (request.name() != null)
			product.setName(request.name());
		if (request.description() != null)
			product.setDescription(request.description());
		if (request.price() != null)
			product.setPrice(request.price());
		if (request.stock() != null)
			product.setStock(request.stock());
		ProductResponse productResponse = response(productRepository.save(product));
		logger.info("Product partially updated with id : {}", productResponse.id());
		return productResponse;
	}

	public void deleteProduct(Long id) {
		findEntity(id); // to find product whether existing or not
		productRepository.deleteById(id);
		logger.info("Product deleted with id : {}", id);
	}

	private Product findEntity(Long id) {
		return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
	}

	private ProductResponse response(Product product) {
		return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice(),
				product.getStock());
	}

	private void saveProductDetails(ProductRequest request, Product product) {
		product.setName(request.name());
		product.setDescription(request.description());
		product.setPrice(request.price());
		product.setStock(request.stock());

	}
}