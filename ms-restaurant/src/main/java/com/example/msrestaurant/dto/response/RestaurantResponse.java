package com.example.msrestaurant.dto.response;

import com.example.msrestaurant.enums.RestaurantStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
@Builder
public class RestaurantResponse {
    Long id;
    String restaurantName;
    String address;
    RestaurantStatus restaurantStatus;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}