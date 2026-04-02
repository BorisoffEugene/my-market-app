package ru.yandex.practicum.mymarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.mymarket.dto.ItemDto;
import ru.yandex.practicum.mymarket.service.CartService;

import java.util.List;

@Controller
@RequestMapping("/cart/items")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /* todo
    @GetMapping
    public String items(Model model) {
        List<ItemDto> items = cartService.items();
        Long total = cartService.total();

        model.addAttribute("items", items);
        model.addAttribute("total", total);

        return "cart";
    }

    @PostMapping
    public String doItemAction(@RequestParam Long id, @RequestParam String action) {
        cartService.changeCount(action, id);

        return "redirect:/cart/items";
    }

     */
}
