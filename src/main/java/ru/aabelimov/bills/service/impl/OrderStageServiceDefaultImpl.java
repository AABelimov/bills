package ru.aabelimov.bills.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aabelimov.bills.dto.CreateOrUpdateOrderStageDto;
import ru.aabelimov.bills.entity.Bill;
import ru.aabelimov.bills.entity.Order;
import ru.aabelimov.bills.entity.OrderStage;
import ru.aabelimov.bills.entity.User;
import ru.aabelimov.bills.mapper.OrderStageMapper;
import ru.aabelimov.bills.repository.OrderStageRepository;
import ru.aabelimov.bills.service.BillService;
import ru.aabelimov.bills.service.OrderStageService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderStageServiceDefaultImpl implements OrderStageService {

    private final OrderStageRepository orderStageRepository;
    private final OrderStageMapper orderStageMapper;
    private final BillService billService;

    @Override
    public OrderStage createOrderStage(CreateOrUpdateOrderStageDto createOrUpdateOrderStageDto, Order order) {
        OrderStage orderStage = orderStageMapper.toEntity(createOrUpdateOrderStageDto);
        orderStage.setOrder(order);
        return orderStageRepository.save(orderStage);
    }

    @Override
    public OrderStage getOrderStageById(Long id) {
        return orderStageRepository.findById(id).orElseThrow(); // TODO:: add exception
    }

    @Override
    public List<OrderStage> getOrderStagesByOrderId(Long orderId) {
        return orderStageRepository.findAllByOrderIdOrderByIdDesc(orderId);
    }

    @Override
    public List<OrderStage> getOrderStagesByOrderIdAndTitle(Long orderId, String title) {
        return orderStageRepository.findAllByOrderIdAndTitleOrderByIdDesc(orderId, title);
    }

    @Override
    public void updateOrderStage(Long id, CreateOrUpdateOrderStageDto dto) {
        OrderStage orderStage = getOrderStageById(id);
        if (!dto.title().isBlank()) {
            orderStage.setTitle(dto.title());
        }
        if (!dto.description().isBlank()) {
            orderStage.setDescription(dto.description());
        }
        orderStageRepository.save(orderStage);
    }

    @Override
    @Transactional
    public void removeOrderStage(OrderStage orderStage) {
        List<Bill> bills = billService.getBillsByOrderStageId(orderStage.getId());
        bills.forEach(billService::removeBill);
        orderStageRepository.delete(orderStage);
    }

    @Override
    public boolean isCorrectKey(Long orderStageId, Integer key) {
        if (key == null) {
            return false;
        }
        OrderStage orderStage = getOrderStageById(orderStageId);
        User user = orderStage.getOrder().getUser();
        return user.hashCode() == key;
    }
}
