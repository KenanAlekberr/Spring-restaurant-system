package com.example.msmenu.dao.repository;

import com.example.msmenu.dao.entity.MenuEntity;
import com.example.msmenu.enums.MenuStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
    Optional<MenuEntity> findByIdAndStatusIn(Long id, List<MenuStatus> statuses);
}