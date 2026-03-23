package ru.yandex.practicum.mymarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.mymarket.domain.Item;
import ru.yandex.practicum.mymarket.service.CartService;

import java.util.List;

@Controller
@RequestMapping("/cart/items")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public String items(Model model) {
        List<Item> items = cartService.items();
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
}
