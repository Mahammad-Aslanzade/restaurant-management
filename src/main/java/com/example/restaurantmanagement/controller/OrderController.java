package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.model.order.OrderCreateDto;
import com.example.restaurantmanagement.model.order.OrderDto;
import com.example.restaurantmanagement.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getAllOrders(){
        return orderService.getAllOrders();
    }

    @PostMapping
    public void createOrder(@RequestBody OrderCreateDto orderCreateDto){
        orderService.createOrder(orderCreateDto);
    }

}
