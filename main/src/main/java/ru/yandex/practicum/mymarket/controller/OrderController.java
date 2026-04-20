package ru.yandex.practicum.mymarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mymarket.service.OrderService;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public Mono<Rendering> findAll() {
        return Mono.just(
                Rendering.view("orders")
                        .modelAttribute("orders", orderService.findAll("user"))//todo
                        .build()
        );
    }

    @GetMapping("/{id}")
    public Mono<Rendering> findById(@PathVariable Long id, @RequestParam(defaultValue = "false") boolean newOrder) {
        return orderService.findById(id)
                .map(order -> Rendering.view("order")
                        .modelAttribute("newOrder", newOrder)
                        .modelAttribute("order", order)
                        .build())
                .switchIfEmpty(Mono.just(Rendering.redirectTo("/orders").build()));
    }
}
