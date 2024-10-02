package com.example.restaurantmanagement.dao.repository;

import com.example.restaurantmanagement.dao.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<AddressEntity, String> {

    List<AddressEntity> findAllByUserId(String userId);

}
