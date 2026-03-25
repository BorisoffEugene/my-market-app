package ru.yandex.practicum.mymarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.yandex.practicum.mymarket.domain.*;
import ru.yandex.practicum.mymarket.service.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/buy")
public class BuyController {
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;

    @PostMapping
    public String buy(Model model) {
        boolean newOrder = true;
        List<Item> items = cartService.items();
        Long totalSum = cartService.total();
        List<OrderItem> orderItems = new ArrayList<>();

        for(Item item : items)
            orderItems.add(new OrderItem(item.getTitle(), item.getCount(), item.getPrice()));

        Order order = orderService.save(new Order(orderItems, totalSum));
        cartService.clearCount();

        model.addAttribute("newOrder", newOrder);
        model.addAttribute("order", order);

        String redirectUrl = UriComponentsBuilder.fromPath("/orders/{id}?newOrder={newOrder}")
                .buildAndExpand(order.getId(), newOrder)
                .toUriString();
        return "redirect:" + redirectUrl;
    }
}
