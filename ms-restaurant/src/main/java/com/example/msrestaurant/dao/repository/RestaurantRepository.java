package com.example.msrestaurant.dao.repository;

import com.example.msrestaurant.dao.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
}