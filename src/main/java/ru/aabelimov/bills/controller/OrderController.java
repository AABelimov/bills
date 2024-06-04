package ru.aabelimov.bills.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.aabelimov.bills.dto.CreateOrUpdateOrderDto;
import ru.aabelimov.bills.entity.Order;
import ru.aabelimov.bills.entity.User;
import ru.aabelimov.bills.service.OrderService;
import ru.aabelimov.bills.service.UserService;

@Controller
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @PostMapping()
    public String createOrder(CreateOrUpdateOrderDto createOrUpdateOrderDto) {
        User user = userService.getUserById(createOrUpdateOrderDto.userId());
        Order order = orderService.createOrder(createOrUpdateOrderDto, user);
        return "redirect:/order-stages/order/%d".formatted(order.getId());
    }

    @GetMapping("user/{userId}")
    public String getOrdersByUserId(@PathVariable Long userId,
                                    @RequestParam(required = false) String title,
                                    Model model) {
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        if (title == null) {
            model.addAttribute("orders", orderService.getOrdersByUserId(userId));
        } else {
            model.addAttribute("orders", orderService.getOrdersByUserIdAndTitle(userId, title));
        }
        return "order/orders";
    }

    @DeleteMapping("{id}")
    public String removeOrder(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        orderService.removeOrder(order);
        return "redirect:/orders/user/%d".formatted(order.getUser().getId());
    }
}
