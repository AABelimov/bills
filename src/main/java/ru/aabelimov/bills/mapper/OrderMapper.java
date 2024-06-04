package ru.aabelimov.bills.mapper;

import org.springframework.stereotype.Component;
import ru.aabelimov.bills.dto.CreateOrUpdateOrderDto;
import ru.aabelimov.bills.entity.Order;

@Component
public class OrderMapper {

    public Order toEntity(CreateOrUpdateOrderDto dto) {
        Order order = new Order();
        order.setTitle(dto.title());
        order.setDescription(dto.description());
        return order;
    }
}
