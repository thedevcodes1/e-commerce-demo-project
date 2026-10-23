package com.online.shopping_app.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.online.shopping_app.dto.ProductPatchRequest;
import com.online.shopping_app.dto.ProductRequest;
import com.online.shopping_app.dto.ProductResponse;
import com.online.shopping_app.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Create, read, update and delete products")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/getAllProducts")
	@Operation(summary = "List products, one page at a time")
	public PagedModel<ProductResponse> getAllProducts(@ParameterObject Pageable pageable) {
		return new PagedModel<>(productService.getAllProducts(pageable));
	}

	@GetMapping("/getProductById/{id}")
	@Operation(summary = "Get a product details by id")
	@ApiResponse(responseCode = "404", description = "Product not found")
	public ProductResponse findProductById(@PathVariable Long id) {
		return productService.findProductById(id);
	}

	@PostMapping("/addProduct")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Create a product")
	@ApiResponse(responseCode = "400", description = "Invalid input")
	public ProductResponse addProduct(@Valid @RequestBody ProductRequest request) {
		return productService.addProduct(request);
	}

	@PutMapping("updateProduct/{id}")
	@Operation(summary = "Update all fields in a product")
	@ApiResponse(responseCode = "400", description = "Invalid input")
	@ApiResponse(responseCode = "404", description = "Product not found")
	public ProductResponse updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
		return productService.updateProduct(id, request);
	}

	@PatchMapping("partialUpdateProduct/{id}")
	@Operation(summary = "Update the selected fields only")
	@ApiResponse(responseCode = "400", description = "Invalid input")
	@ApiResponse(responseCode = "404", description = "Product not found")
	public ProductResponse partialUpdateProduct(@PathVariable Long id, @Valid @RequestBody ProductPatchRequest request) {
	    return productService.partialUpdateProduct(id, request);
	}

	@DeleteMapping("deleteProduct/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Delete a product")
	@ApiResponse(responseCode = "404", description = "Product not found")
	public void deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
	}

}