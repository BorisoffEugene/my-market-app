package ru.yandex.practicum.mymarket.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public Mono<Rendering> items(@AuthenticationPrincipal UserDetails userDetails) {
        return Mono.just(
                Rendering.view("cart")
                        .modelAttribute("items", cartService.items(userDetails != null ? userDetails.getUsername() : null))
                        .modelAttribute("total", cartService.total(userDetails != null ? userDetails.getUsername() : null))
                        .modelAttribute("check", "OK"/*paymentService.check(cartService.total(userDetails.getUsername()))*/) //todo
                        .build()
        );
    }

    @PostMapping
    public Mono<String> doItemAction(@RequestParam Long id, @RequestParam String action, @AuthenticationPrincipal UserDetails userDetails) {
        return cartService.changeCount(action, id, userDetails != null ? userDetails.getUsername() : null).thenReturn("redirect:/cart/items");
    }
}
