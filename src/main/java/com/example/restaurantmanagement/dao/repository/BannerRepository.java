package com.example.restaurantmanagement.dao.repository;

import com.example.restaurantmanagement.dao.entity.BannerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BannerRepository extends JpaRepository<BannerEntity, String> {

    Optional<BannerEntity> getBannerEntityByNameIgnoreCase(String name);
}
