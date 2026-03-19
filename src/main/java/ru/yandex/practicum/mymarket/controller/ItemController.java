package ru.yandex.practicum.mymarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.yandex.practicum.mymarket.service.ItemService;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/admin-items")
    public String adminFindAll(Model model) {
        model.addAttribute("message", "Привет, Thymeleaf!");
        return "admin-items";
    }
}
