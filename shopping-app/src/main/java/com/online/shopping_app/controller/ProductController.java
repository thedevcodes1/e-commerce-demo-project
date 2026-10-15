package com.online.shopping_app.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.online.shopping_app.dto.ProductRequest;
import com.online.shopping_app.dto.ProductResponse;
import com.online.shopping_app.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/getAllProducts")
	public PagedModel<ProductResponse> getAllProducts(Pageable pageable) {
		return new PagedModel<>(productService.getAllProducts(pageable));
	}

	@GetMapping("/getProductById/{id}")
	public ProductResponse findProductById(@PathVariable Long id) {
		return productService.findProductById(id);
	}

	@PostMapping("/addProduct")
	@ResponseStatus(HttpStatus.CREATED)
	public ProductResponse addProduct(@Valid @RequestBody ProductRequest request) {
		return productService.addProduct(request);
	}

	@PutMapping("updateProduct/{id}")
	public ProductResponse updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
		return productService.updateProduct(id, request);
	}

	@DeleteMapping("deleteProduct/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
	}

}