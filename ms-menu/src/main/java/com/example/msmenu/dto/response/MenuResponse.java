package com.example.msmenu.dto.response;

import com.example.msmenu.enums.MenuStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
@Builder
public class MenuResponse {
    Long id;
    Long restaurantId;
    String productName;
    BigDecimal price;
    MenuStatus menuStatus;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}