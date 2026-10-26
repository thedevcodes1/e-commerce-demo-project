package com.online.shopping_app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.online.shopping_app.dto.ProductPatchRequest;
import com.online.shopping_app.dto.ProductRequest;
import com.online.shopping_app.dto.ProductResponse;
import com.online.shopping_app.exception.ProductNotFoundException;
import com.online.shopping_app.model.Product;
import com.online.shopping_app.repository.ProductRepository;
import com.online.shopping_app.service.ProductService;

@ExtendWith(MockitoExtension.class)

public class ProductServiceTest {

	@Mock
	private ProductRepository mockRepository;

	@InjectMocks
	private ProductService productService;
	
	@Test
	void findALLProducts_returnProductsList() {
		
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<Product> page = new PageImpl<>(List.of(laptop(), mouse(), keyboard()), pageable, 3);
		
		when(mockRepository.findAll(pageable)).thenReturn(page);

		Page<ProductResponse> result = productService.getAllProducts(pageable);

		assertThat(result.getTotalElements()).isEqualTo(3);
		assertThat(result.getContent()).hasSize(3);
		
		assertThat(result.getContent().get(0).name()).isEqualTo("Laptop");
		assertThat(result.getContent().get(1).name()).isEqualTo("Mouse");
		assertThat(result.getContent().get(2).name()).isEqualTo("Keyboard");
	}

	
	@Test
	void findProductById_thenProductReturn() {
		
		when(mockRepository.findById(1L)).thenReturn(Optional.of(laptop()));

		ProductResponse result = productService.findProductById(1L);

		assertThat(result.id()).isEqualTo(1L);
		assertThat(result.name()).isEqualTo("Laptop");
		assertThat(result.description()).isEqualTo("14 inch");
		assertThat(result.price()).isEqualTo("55000");
		assertThat(result.stock()).isEqualTo(10);
	}

	
	@Test
	void findProductById_throwsException_whenProductNotFound() {
		
		when(mockRepository.findById(99L)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> productService.findProductById(99L)).isInstanceOf(ProductNotFoundException.class)
				.hasMessage("Product Not Found : 99");
	}

	
	@Test
	void addProduct_returnsResponse() {

		ProductRequest request = new ProductRequest("Mouse", "Wireless", new BigDecimal("999"), 25);

		when(mockRepository.save(any(Product.class))).thenAnswer(invocation -> {
			Product saved = invocation.getArgument(0);
			saved.setId(5L);
			return saved;
		});

		ProductResponse result = productService.addProduct(request);

		assertThat(result.id()).isEqualTo(5L);
		assertThat(result.name()).isEqualTo("Mouse");
		assertThat(result.description()).isEqualTo("Wireless");
		assertThat(result.price()).isEqualTo("999");
		assertThat(result.stock()).isEqualTo(25);
		verify(mockRepository).save(any(Product.class));
	}

	
	@Test
	void updateProductById_whenProductFound() {

		when(mockRepository.findById(1L)).thenReturn(Optional.of(laptop()));

		when(mockRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

		ProductRequest request = new ProductRequest("Gaming Laptop", "16 inch", new BigDecimal("80000"), 4);

		ProductResponse result = productService.updateProduct(1L, request);

		assertThat(result.name()).isEqualTo("Gaming Laptop");
		assertThat(result.description()).isEqualTo("16 inch");
		assertThat(result.price()).isEqualByComparingTo("80000");
		assertThat(result.stock()).isEqualTo(4);
	}

	
	@Test
	void updateProductById_throwsException_whenProductNotFound() {

		when(mockRepository.findById(99L)).thenReturn(Optional.empty());

		ProductRequest request = new ProductRequest("Gaming Laptop", "16 inch", new BigDecimal("80000"), 4);

		assertThatThrownBy(() -> productService.updateProduct(99L, request))
				.isInstanceOf(ProductNotFoundException.class).hasMessage("Product Not Found : 99");

		verify(mockRepository, never()).save(any(Product.class));
	}

	
	@Test
	void deleteProduct_whenFound() {

		when(mockRepository.findById(2L)).thenReturn(Optional.of(mouse()));

		productService.deleteProduct(2L);

		verify(mockRepository).deleteById(2L);
	}

	
	@Test
	void deleteProduct_throwsException_whenProductNotFound() {

		when(mockRepository.findById(99L)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> productService.deleteProduct(99L)).isInstanceOf(ProductNotFoundException.class);

		verify(mockRepository, never()).deleteById(any());
	}


	@Test
	void partialUpdateProduct_changesOnlySentFields_whenFound() {
		
	    when(mockRepository.findById(1L)).thenReturn(Optional.of(mouse()));
	    
	    when(mockRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
	    
	    ProductPatchRequest request = new ProductPatchRequest(null, null, new BigDecimal("990"), null);

	    ProductResponse result = productService.partialUpdateProduct(1L, request);

	    assertThat(result.price()).isEqualByComparingTo("990");
	    assertThat(result.name()).isEqualTo("Mouse");
	    assertThat(result.description()).isEqualTo("Wireless mouse");
	    assertThat(result.stock()).isEqualTo(25);
	}

	
	@Test
	void partialUpdateProduct_throwsException_whenMissing() {
		
	    Long missingId = 99L;
	    
	    when(mockRepository.findById(missingId)).thenReturn(Optional.empty());
	    
	    ProductPatchRequest request = new ProductPatchRequest(null, null, new BigDecimal("52000"), null);

	    assertThatThrownBy(() -> productService.partialUpdateProduct(missingId, request))
	            .isInstanceOf(ProductNotFoundException.class);

	    verify(mockRepository, never()).save(any(Product.class));
	}
	
	
	private Product laptop() {
		Product p = new Product();
		p.setId(1L);
		p.setName("Laptop");
		p.setDescription("14 inch");
		p.setPrice(new BigDecimal("55000"));
		p.setStock(10);
		return p;
	}

	private Product mouse() {
		Product p = new Product();
		p.setId(2L);
		p.setName("Mouse");
		p.setDescription("Wireless mouse");
		p.setPrice(new BigDecimal("800.00"));
		p.setStock(25);
		return p;
	}

	private Product keyboard() {
		Product p = new Product();
		p.setId(3L);
		p.setName("Keyboard");
		p.setDescription("Logitech");
		p.setPrice(new BigDecimal("650.00"));
		p.setStock(10);
		return p;
	}

}
