package ru.aabelimov.bills.mapper;

import org.springframework.stereotype.Component;
import ru.aabelimov.bills.dto.CreateOrUpdateOrderStageDto;
import ru.aabelimov.bills.entity.OrderStage;

@Component
public class OrderStageMapper {

    public OrderStage toEntity(CreateOrUpdateOrderStageDto dto) {
        OrderStage orderStage = new OrderStage();
        orderStage.setTitle(dto.title());
        orderStage.setDescription(dto.description());
        return orderStage;
    }
}
