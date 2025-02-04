package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.enums.PaymentType;
import com.example.restaurantmanagement.model.order.OrderCreateDto;
import com.example.restaurantmanagement.model.order.OrderDto;
import com.example.restaurantmanagement.model.order.OrderUpdateDto;
import com.example.restaurantmanagement.model.order.UpdateStatusDto;
import com.example.restaurantmanagement.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/user")
    public List<OrderDto> getCurrentUserOrders() {
        return orderService.getCurrentUserOrders();
    }

    @GetMapping("/user/{user-id}")
    public List<OrderDto> getUserOrders(@PathVariable("user-id") String userId) {
        return orderService.getUserOrders(userId);
    }

    @GetMapping("/{order-id}")
    public OrderDto getOrderById(@PathVariable("order-id") String orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/type/{order-type}")
    public List<OrderDto> filterByType(@PathVariable("order-type") String orderType) {
        return orderService.filterByType(orderType);
    }

    @GetMapping("/payment-method/{payment-method}")
    public List<OrderDto> filterByPaymentMethod(@PathVariable("payment-method") PaymentType paymentMethod) {
        return orderService.filterByPaymentMethod(paymentMethod);
    }

    @PostMapping
    public void createOrder(@RequestBody OrderCreateDto orderCreateDto) {
        orderService.createOrder(orderCreateDto);
    }

    @PatchMapping("/{order-id}/change-status")
    public void updateOrderStatus(@PathVariable("order-id") String orderId, @RequestBody UpdateStatusDto updateStatusDto) {
        orderService.updateOrderStatus(orderId, updateStatusDto);
    }

    @PutMapping("/{order-id}")
    public void updateOrder(@PathVariable("order-id") String orderId, @RequestBody OrderUpdateDto orderUpdateDto) {
        orderService.updateOrder(orderId, orderUpdateDto);
    }
}
