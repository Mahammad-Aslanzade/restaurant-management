package com.example.restaurantmanagement.dao.repository.jpa;

import com.example.restaurantmanagement.dao.entity.AddressEntity;
import com.example.restaurantmanagement.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<AddressEntity, String> {

    List<AddressEntity> findAllByUser(UserEntity user);

}
