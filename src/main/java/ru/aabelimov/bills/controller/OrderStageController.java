package ru.aabelimov.bills.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.aabelimov.bills.dto.CreateOrUpdateOrderStageDto;
import ru.aabelimov.bills.entity.Order;
import ru.aabelimov.bills.entity.OrderStage;
import ru.aabelimov.bills.service.OrderService;
import ru.aabelimov.bills.service.OrderStageService;

@Controller
@RequestMapping("order-stages")
@RequiredArgsConstructor
public class OrderStageController {

    private final OrderStageService orderStageService;
    private final OrderService orderService;

    @PostMapping
    public String createOrderStage(CreateOrUpdateOrderStageDto createOrUpdateOrderStageDto) {
        Order order = orderService.getOrderById(createOrUpdateOrderStageDto.orderId());
        OrderStage orderStage = orderStageService.createOrderStage(createOrUpdateOrderStageDto, order);
        return "redirect:/bills/order-stage/%d".formatted(orderStage.getId());
    }

    @GetMapping("order/{orderId}")
    public String getOrderStagesByOrderId(@PathVariable Long orderId,
                                          @RequestParam(required = false) String title,
                                          Model model) {
        Order order = orderService.getOrderById(orderId);
        model.addAttribute("user", order.getUser());
        model.addAttribute("order", order);
        if (title == null) {
            model.addAttribute("orderStages", orderStageService.getOrderStagesByOrderId(orderId));
        } else {
            model.addAttribute("orderStages", orderStageService.getOrderStagesByOrderIdAndTitle(orderId, title));
        }
        return "order_stage/order-stages";
    }

    @GetMapping("{id}/update")
    public String getOrderStageUpdatePage(@PathVariable Long id, Model model) {
        model.addAttribute("orderStage", orderStageService.getOrderStageById(id));
        return "order_stage/order-stage-edit";
    }

    @PatchMapping("{id}")
    public String updateOrderStage(@PathVariable Long id, CreateOrUpdateOrderStageDto createOrUpdateOrderStageDto) {
        orderStageService.updateOrderStage(id, createOrUpdateOrderStageDto);
        return "redirect:/bills/order-stage/%d".formatted(id);
    }

    @DeleteMapping("{id}")
    public String removeOrderStage(@PathVariable Long id) {
        OrderStage orderStage = orderStageService.getOrderStageById(id);
        orderStageService.removeOrderStage(orderStage);
        return "redirect:/order-stages/order/%d".formatted(orderStage.getOrder().getId());
    }
}
