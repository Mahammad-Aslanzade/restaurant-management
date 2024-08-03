package com.example.restaurantmanagement.service;

import com.example.restaurantmanagement.dao.entity.AddressEntity;
import com.example.restaurantmanagement.dao.entity.OrderEntity;
import com.example.restaurantmanagement.dao.entity.UserEntity;
import com.example.restaurantmanagement.dao.repository.OrderRepository;
import com.example.restaurantmanagement.enums.ExceptionDetails;
import com.example.restaurantmanagement.enums.OrderStatus;
import com.example.restaurantmanagement.enums.OrderType;
import com.example.restaurantmanagement.exceptions.NotFoundException;
import com.example.restaurantmanagement.mapper.OrderMapper;
import com.example.restaurantmanagement.model.order.OrderCreateDto;
import com.example.restaurantmanagement.model.order.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    private final MealService mealService;
    private final UserService userService;

    public List<OrderDto> getAllOrders() {
        log.info("ACTION.getAllOrders.start");
        List<OrderEntity> orderEntityList = orderRepository.findAll();
        List<OrderDto> orderDtoList = orderMapper.listToDto(orderEntityList);
        log.info("ACTION.getAllOrders.end");
        return orderDtoList;
    }

    private OrderEntity getOrderEntity(String orderId){
        return orderRepository.findById(orderId).
                orElseThrow(() ->
                        new NotFoundException(
                                ExceptionDetails.ORDER_NOT_FOUND.message(),
                                ExceptionDetails.ORDER_NOT_FOUND.createLogMessage("getOrderEntity", orderId)
                        )
                );
    }

    public OrderDto getOrderById(String orderId) {
        log.info("ACTION.getOrderById.start orderId : {}", orderId);
        OrderEntity orderEntity = getOrderEntity(orderId);
        OrderDto orderDto = orderMapper.mapToDto(orderEntity);
        log.info("ACTION.getOrderById.end orderId : {}", orderId);
        return orderDto;
    }

    public void createOrder(OrderCreateDto orderCreateDto){
        log.info("ACTION.createOrder.start requestBody : {}", orderCreateDto);
        OrderEntity order = orderMapper.mapToEntity(orderCreateDto);
        // User and Address
        UserEntity user = userService.getUserEntity(orderCreateDto.getUserId());
        AddressEntity address = userService.haveThisAddress(orderCreateDto.getUserId(), orderCreateDto.getAddressId());
        order.setUser(user);
        order.setAddress(address);
        // Total amount
        Double totalPrice = mealService.calculateTotalPrice(orderCreateDto.getMealList());
        order.setTotalPrice(totalPrice);
        //Set default status
        if(orderCreateDto.getType().equals(OrderType.PLANNED)){
            order.setStatus(OrderStatus.PLANNED);
        }else{
            order.setStatus(OrderStatus.PREPARING);
        }
        orderRepository.save(order);
        log.info("ACTION.createOrder.end requestBody : {}", orderCreateDto);
    }

}
