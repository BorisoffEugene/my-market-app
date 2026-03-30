package ru.yandex.practicum.mymarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.mymarket.dto.OrderDto;
import ru.yandex.practicum.mymarket.service.OrderService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String findAll(Model model) {
        List<OrderDto> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, @RequestParam(defaultValue = "false") boolean newOrder, Model model) {
        Optional<OrderDto> order = orderService.findById(id);
        if (order.isPresent()) {
            model.addAttribute("newOrder", newOrder);
            model.addAttribute("order", order.get());
            return "order";
        }

        return "redirect:/orders";
    }
}
