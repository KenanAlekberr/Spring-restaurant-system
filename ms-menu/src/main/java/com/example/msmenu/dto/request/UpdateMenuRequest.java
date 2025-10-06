package com.example.msmenu.dto.request;

import jakarta.validation.constraints.DecimalMin;
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
public class UpdateMenuRequest {
    @Size(min = 2, max = 50, message = "Product name length must be between 2 and 50")
    String productName;

    @DecimalMin(value = "0.00", message = "Price cannot be negative")
    BigDecimal price;
}