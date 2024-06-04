package ru.aabelimov.bills.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aabelimov.bills.dto.CreateOrUpdateOrderDto;
import ru.aabelimov.bills.entity.Order;
import ru.aabelimov.bills.entity.OrderStage;
import ru.aabelimov.bills.entity.User;
import ru.aabelimov.bills.mapper.OrderMapper;
import ru.aabelimov.bills.repository.OrderRepository;
import ru.aabelimov.bills.service.OrderService;
import ru.aabelimov.bills.service.OrderStageService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceDefaultImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderStageService orderStageService;

    @Override
    public Order createOrder(CreateOrUpdateOrderDto createOrUpdateOrderDto, User user) {
        Order order = orderMapper.toEntity(createOrUpdateOrderDto);
        order.setUser(user);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(); // TODO :: add exception
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @Override
    public List<Order> getOrdersByUserIdAndTitle(Long userId, String title) {
        return orderRepository.findAllByUserIdAndTitle(userId, title);
    }

    @Override
    @Transactional
    public void removeOrder(Order order) {
        List<OrderStage> orderStages = orderStageService.getOrderStagesByOrderId(order.getId());
        orderStages.forEach(orderStageService::removeOrderStage);
        orderRepository.delete(order);
    }
}
