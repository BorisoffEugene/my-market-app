package ru.yandex.practicum.mymarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mymarket.service.CartService;

@Controller
@RequestMapping("/cart/items")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public Mono<Rendering> items() {
        return Mono.just(
                Rendering.view("cart")
                        .modelAttribute("items", cartService.items())
                        .modelAttribute("total", cartService.total())
                        .build()
        );
    }

    @PostMapping
    public Mono<String> doItemAction(@RequestParam Long id, @RequestParam String action) {
        return cartService.changeCount(action, id).thenReturn("redirect:/cart/items");
    }
}
