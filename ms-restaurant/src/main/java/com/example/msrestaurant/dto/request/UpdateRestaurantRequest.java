package com.example.msrestaurant.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class UpdateRestaurantRequest {
    @Size(min = 2, max = 50, message = "Restaurant name length must be between 2 and 50")
    String restaurantName;

    @Size(min = 2, max = 100, message = "Restaurant address length must be between 2 and 100")
    String address;
}