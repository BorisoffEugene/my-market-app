package ru.yandex.practicum.mymarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.mymarket.domain.Item;
import ru.yandex.practicum.mymarket.service.ItemService;

import java.util.List;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/admin-items")
    public String adminItemsFindAll(Model model) {
        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);
        return "admin-items";
    }

    @GetMapping("/admin-items/new")
    public String adminItemsAdd(Model model) {
        model.addAttribute("item", new Item());
        return "admin-item";
    }
}
