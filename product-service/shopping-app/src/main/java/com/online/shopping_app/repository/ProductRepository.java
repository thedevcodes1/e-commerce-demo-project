package com.online.shopping_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online.shopping_app.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
}