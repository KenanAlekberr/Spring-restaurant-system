package com.example.msrestaurant.dao.repository;

import com.example.msrestaurant.dao.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    @Query(value = "SELECT * FROM restaurants r WHERE r.id = :id AND r.restaurant_status IN ('ACTIVE', 'IN_PROGRESS')", nativeQuery = true)
    Optional<RestaurantEntity> findById(@Param("id") Long id);
}