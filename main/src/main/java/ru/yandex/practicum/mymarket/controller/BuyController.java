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
    private final PaymentService paymentService;
    private final CartService cartService;

    public BuyController(OrderService orderService, PaymentService paymentService, CartService cartService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.cartService = cartService;
    }

    @PostMapping
    public Mono<Rendering> buy() {
        return paymentService.debit(cartService.total("user")) //todo
                .flatMap(str -> {
                   if (str.equals("OK"))
                       return orderService.sold("user") //todo
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
                   else
                       return cartService.total("user") //todo
                               .map(total -> Rendering.view("cart")
                                       .modelAttribute("items", cartService.items("user"))//todo
                                       .modelAttribute("total", total)
                                       .modelAttribute("check", str)
                                       .build());
                });
    }
}
