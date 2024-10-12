package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.model.order.OrderCreateDto;
import com.example.restaurantmanagement.model.order.OrderDto;
import com.example.restaurantmanagement.model.order.OrderUpdateDto;
import com.example.restaurantmanagement.model.order.UpdateStatusDto;
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
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/user")
    public List<OrderDto> getCurrentUserOrders(){
        return orderService.getCurrentUserOrders();
    }

    @GetMapping("/user/{userId}")
    public List<OrderDto> getUserOrders(@PathVariable String userId){
        return orderService.getUserOrders(userId);
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrderById(@PathVariable String orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/type/{orderType}")
    public List<OrderDto> filterByType(@PathVariable String orderType) {
        return orderService.filterByType(orderType);
    }

    @GetMapping("/paymentMethod/{paymentMethod}")
    public List<OrderDto> filterByPaymentMethod(@PathVariable String paymentMethod) {
        return orderService.filterByPaymentMethod(paymentMethod);
    }

    @PostMapping
    public void createOrder(@RequestBody OrderCreateDto orderCreateDto) {
        orderService.createOrder(orderCreateDto);
    }

    @PatchMapping("/{orderId}/changeStatus")
    public void updateOrderStatus(@PathVariable String orderId, @RequestBody UpdateStatusDto updateStatusDto) {
        orderService.updateOrderStatus(orderId, updateStatusDto);
    }

    @PutMapping("/{orderId}")
    public void updateOrder(@PathVariable String orderId , @RequestBody OrderUpdateDto orderUpdateDto){
        orderService.updateOrder(orderId , orderUpdateDto);
    }
}
