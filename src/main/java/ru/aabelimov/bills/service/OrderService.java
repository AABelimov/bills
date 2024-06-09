package ru.aabelimov.bills.service;

import ru.aabelimov.bills.dto.CreateOrUpdateOrderDto;
import ru.aabelimov.bills.entity.Order;
import ru.aabelimov.bills.entity.User;

import java.util.List;

public interface OrderService {
    Order createOrder(CreateOrUpdateOrderDto createOrUpdateOrderDto, User user);

    Order getOrderById(Long orderId);

    List<Order> getOrdersByUserId(Long userId);

    List<Order> getOrdersByUserIdAndTitle(Long userId, String title);

    void updateOrder(Long id, CreateOrUpdateOrderDto dto);

    void removeOrder(Order order);
}
