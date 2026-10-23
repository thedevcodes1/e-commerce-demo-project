package com.online.shopping_app.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductRequest(
		
        @NotBlank(message = "Name is required") 
        @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9\\s]*$", message = "Name should not start with zero")
        String name,
        
        String description,
        
        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than 0") 
        BigDecimal price,
        
        @NotNull(message = "Stock is required")
        @PositiveOrZero(message = "Stock cannot be negative") 
        Integer stock )
{
	
	
}