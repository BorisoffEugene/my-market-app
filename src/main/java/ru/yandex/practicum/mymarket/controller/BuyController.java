package ru.yandex.practicum.mymarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.yandex.practicum.mymarket.domain.*;
import ru.yandex.practicum.mymarket.dto.ItemDto;
import ru.yandex.practicum.mymarket.dto.OrderDto;
import ru.yandex.practicum.mymarket.service.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/buy")
public class BuyController {
    private final CartService cartService;
    private final OrderService orderService;

    public BuyController(CartService cartService, OrderService orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @PostMapping
    public String buy(Model model) {
        boolean newOrder = true;
        List<ItemDto> items = cartService.items();
        Long totalSum = cartService.total();
        List<OrderItem> orderItems = new ArrayList<>();

        for(ItemDto item : items)
            orderItems.add(new OrderItem(item.getTitle(), item.getCount(), item.getPrice()));

        OrderDto order = orderService.save(new OrderDto(orderItems, totalSum));
        cartService.sold();

        model.addAttribute("newOrder", newOrder);
        model.addAttribute("order", order);

        String redirectUrl = UriComponentsBuilder.fromPath("/orders/{id}?newOrder={newOrder}")
                .buildAndExpand(order.getId(), newOrder)
                .toUriString();
        return "redirect:" + redirectUrl;
    }
}
