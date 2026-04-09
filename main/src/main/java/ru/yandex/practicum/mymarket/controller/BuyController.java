package ru.yandex.practicum.mymarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mymarket.service.*;

@Controller
@RequestMapping("/buy")
public class BuyController {
    private final OrderService orderService;

    public BuyController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Mono<Rendering> buy() {
        return orderService.sold()
                .map(order -> {
                    boolean newOrder = true;

                    String redirectUrl = UriComponentsBuilder.fromPath("/orders/{id}?newOrder={newOrder}")
                            .buildAndExpand(order.getId(), newOrder)
                            .toUriString();

                    return Rendering.redirectTo(redirectUrl)
                            .modelAttribute("newOrder", newOrder)
                            .modelAttribute("order", order)
                            .build();
                });
    }
}
