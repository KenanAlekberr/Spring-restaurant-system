package com.example.msmenu.dao.repository;

import com.example.msmenu.dao.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
}