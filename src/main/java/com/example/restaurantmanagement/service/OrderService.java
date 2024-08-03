package com.example.restaurantmanagement.service;

import com.example.restaurantmanagement.dao.repository.OrderRepository;
import com.example.restaurantmanagement.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

}
