package ru.yandex.practicum.mymarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mymarket.service.CartService;
import ru.yandex.practicum.mymarket.service.PaymentService;

@Controller
@RequestMapping("/cart/items")
public class CartController {
    private final CartService cartService;
    private final PaymentService paymentService;

    public CartController(CartService cartService, PaymentService paymentService) {
        this.cartService = cartService;
        this.paymentService = paymentService;
    }

    @GetMapping
    public Mono<Rendering> items() {
        return Mono.just(
                Rendering.view("cart")
                        .modelAttribute("items", cartService.items())
                        .modelAttribute("total", cartService.total())
                        .modelAttribute("check", paymentService.check(cartService.total()))
                        .build()
        );
    }

    @PostMapping
    public Mono<String> doItemAction(@RequestParam Long id, @RequestParam String action) {
        return cartService.changeCount(action, id).thenReturn("redirect:/cart/items");
    }
}
