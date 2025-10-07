package com.example.msrestaurant.dao.repository;

import com.example.msrestaurant.dao.entity.RestaurantEntity;
import com.example.msrestaurant.enums.RestaurantStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    Optional<RestaurantEntity> findByIdAndStatusIn(Long id, List<RestaurantStatus> statuses);
}