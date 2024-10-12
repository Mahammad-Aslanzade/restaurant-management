package com.example.restaurantmanagement.service;

import com.example.restaurantmanagement.dao.entity.AddressEntity;
import com.example.restaurantmanagement.dao.entity.OrderEntity;
import com.example.restaurantmanagement.dao.entity.UserEntity;
import com.example.restaurantmanagement.dao.repository.jpa.OrderRepository;
import com.example.restaurantmanagement.enums.ExceptionDetails;
import com.example.restaurantmanagement.enums.OrderStatus;
import com.example.restaurantmanagement.enums.OrderType;
import com.example.restaurantmanagement.enums.PaymentType;
import com.example.restaurantmanagement.exceptions.InvalidException;
import com.example.restaurantmanagement.exceptions.NotAllowedException;
import com.example.restaurantmanagement.exceptions.NotFoundException;
import com.example.restaurantmanagement.mapper.OrderMapper;
import com.example.restaurantmanagement.model.order.OrderCreateDto;
import com.example.restaurantmanagement.model.order.OrderDto;
import com.example.restaurantmanagement.model.order.OrderUpdateDto;
import com.example.restaurantmanagement.model.order.UpdateStatusDto;
import jakarta.validation.Valid;
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
    private final MyUserDetailService userDetailService;

    public List<OrderDto> getAllOrders() {
        log.info("ACTION.getAllOrders.start");
        List<OrderEntity> orderEntityList = orderRepository.findAll();
        List<OrderDto> orderDtoList = orderMapper.listToDto(orderEntityList);
        log.info("ACTION.getAllOrders.end");
        return orderDtoList;
    }

    public List<OrderDto> getUserOrders() {
        UserEntity user = userDetailService.getCurrentAuthenticatedUser();
        log.info("ACTION.getUserOrders.start userId : {}", user.getId());
        List<OrderEntity> orderEntityList = orderRepository.findAllByUser(user);
        List<OrderDto> orderDtoList = orderMapper.listToDto(orderEntityList);
        log.info("ACTION.getUserOrders.end userId : {}", user.getId());
        return orderDtoList;
    }

    private OrderEntity getOrderEntity(String orderId) {
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

    public List<OrderDto> filterByType(String orderType) {
        log.info("ACTION.filterByType.start orderType : {}", orderType);
        OrderType type;
        try {
            type = OrderType.convertToEnum(orderType);
        } catch (IllegalArgumentException ex) {
            throw new InvalidException(
                    orderType,
                    "is not valid type of order",
                    String.format("ACTION.ERROR.convertToEnum orderType : %s", orderType)
            );
        }
        List<OrderEntity> orderEntityList = orderRepository.findAllByType(type);
        List<OrderDto> orderDtoList = orderMapper.listToDto(orderEntityList);
        log.info("ACTION.filterByType.end orderType : {}", orderType);
        return orderDtoList;
    }

    public List<OrderDto> filterByPaymentMethod(String paymentMethod) {
        log.info("ACTION.filterByPaymentMethod.start method : {}", paymentMethod);
        PaymentType type;
        try {
            type = PaymentType.convertToEnum(paymentMethod);
        } catch (IllegalArgumentException ex) {
            throw new InvalidException(
                    paymentMethod,
                    "is not valid method of payment",
                    String.format("ACTION.ERROR.convertToEnum paymentType : %s", paymentMethod)
            );
        }
        List<OrderEntity> orderEntityList = orderRepository.findAllByPaymentType(type);
        List<OrderDto> orderDtoList = orderMapper.listToDto(orderEntityList);
        log.info("ACTION.filterByPaymentMethod.end method : {}", paymentMethod);
        return orderDtoList;
    }

    private void checkOrderBelongTo(OrderEntity order ,UserEntity user){
        if(order.getUser() != user){
            throw new NotAllowedException(
                    "This order not belong to you !",
                    String.format("ACTION.ERROR.checkOrderBelogTo userId : %s | orderId : %s", user.getId() , order.getId())
            );
        }
    }

    public void createOrder(OrderCreateDto orderCreateDto) {
        log.info("ACTION.createOrder.start requestBody : {}", orderCreateDto);
        OrderEntity order = orderMapper.mapToEntity(orderCreateDto);
        // User and Address
        UserEntity user = userDetailService.getCurrentAuthenticatedUser();
        AddressEntity address = userService.haveThisAddress(user, orderCreateDto.getAddressId());
        order.setUser(user);
        order.setAddress(address);
        // Total amount
        Double totalPrice = mealService.calculateTotalPrice(orderCreateDto.getMealList());
        order.setTotalPrice(totalPrice);
        //Set default status
        if (orderCreateDto.getType().equals(OrderType.PLANNED)) {
            order.setStatus(OrderStatus.PLANNED);
        } else {
            order.setStatus(OrderStatus.PREPARING);
        }
        orderRepository.save(order);
        log.info("ACTION.createOrder.end requestBody : {}", orderCreateDto);
    }

    public void updateOrderStatus(String orderId, @Valid UpdateStatusDto updateStatusDto) {
        UserEntity user = userDetailService.getCurrentAuthenticatedUser();
        OrderStatus status = updateStatusDto.getStatus();
        log.info("ACTION.updateOrderStatus.start orderId : {} | status : {}", orderId, status);
        OrderEntity order = getOrderEntity(orderId);
        checkOrderBelongTo(order , user);
        order.setStatus(status);
        orderRepository.save(order);
        log.info("ACTION.updateOrderStatus.end orderId : {} | status : {}", orderId, status);
    }

    public void updateOrder(String orderId, OrderUpdateDto orderUpdateDto) {
        log.info("ACTION.updateOrder.start orderId : {} | requestDto : {}", orderId, orderUpdateDto);
        OrderEntity oldOrder = getOrderEntity(orderId);
        OrderEntity updatedOrder = orderMapper.mapToEntity(orderUpdateDto);
        updatedOrder.setId(oldOrder.getId());
        // User and Address
        UserEntity user = userDetailService.getCurrentAuthenticatedUser();
        // Check USER & ORDER realation
        checkOrderBelongTo(oldOrder , user);
        AddressEntity address = userService.haveThisAddress(user, orderUpdateDto.getAddressId());
        updatedOrder.setUser(user);
        updatedOrder.setAddress(address);
        // Total amount
        Double totalPrice = mealService.calculateTotalPrice(orderUpdateDto.getMealList());
        log.info("HERE-----------{}",totalPrice);
        updatedOrder.setTotalPrice(totalPrice);
        orderRepository.save(updatedOrder);
        log.info("ACTION.updateOrder.end orderId : {} | requestDto : {}", orderId, orderUpdateDto);
    }

}
