package com.example.msmenu.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class CreateMenuRequest {
    @NotNull(message = "Restaurant id cannot be empty")
    Long restaurantId;

    @NotBlank(message = "Product name cannot be empty")
    @Size(min = 2, max = 50, message = "Product name length must be between 2 and 50")
    String productName;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.00", message = "Price cannot be negative")
    BigDecimal price;
}