package ru.aabelimov.bills.service;

import ru.aabelimov.bills.dto.CreateOrUpdateOrderStageDto;
import ru.aabelimov.bills.entity.Order;
import ru.aabelimov.bills.entity.OrderStage;

import java.util.List;

public interface OrderStageService {
    OrderStage createOrderStage(CreateOrUpdateOrderStageDto createOrUpdateOrderStageDto, Order order);

    OrderStage getOrderStageById(Long id);

    List<OrderStage> getOrderStagesByOrderId(Long orderId);

    List<OrderStage> getOrderStagesByOrderIdAndTitle(Long orderId, String title);

    void updateOrderStage(Long id, CreateOrUpdateOrderStageDto dto);

    void removeOrderStage(OrderStage orderStage);

    boolean isCorrectKey(Long orderStageId, Integer key);
}
