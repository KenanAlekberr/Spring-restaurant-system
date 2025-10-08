package com.example.msmenu.dao.repository;

import com.example.msmenu.dao.entity.MenuEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
    @Query(value = "SELECT * FROM menus m WHERE m.id = :id AND m.menu_status IN ('ACTIVE', 'IN_PROGRESS')", nativeQuery = true)
    Optional<MenuEntity> findById(@Param("id") Long id);
}