package com.online.shopping_app.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductPatchRequest(
		
        @Pattern(regexp = ".*\\S.*", message = "Name must not be blank")
        String name,
        
        String description,
        
        @Positive(message = "Price must be greater than 0")
        BigDecimal price,
        
        @PositiveOrZero(message = "Stock cannot be negative")
        Integer stock) {
}
