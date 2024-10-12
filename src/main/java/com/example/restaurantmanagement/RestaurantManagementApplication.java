package com.example.restaurantmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.example.restaurantmanagement.dao.repository.jpa")
public class RestaurantManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantManagementApplication.class, args);
    }

}
