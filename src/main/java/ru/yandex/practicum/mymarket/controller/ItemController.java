package ru.yandex.practicum.mymarket.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.yandex.practicum.mymarket.dto.ItemDto;
import ru.yandex.practicum.mymarket.service.CartService;
import ru.yandex.practicum.mymarket.service.ItemService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping({"/items", "/"})
public class ItemController {
    private final ItemService itemService;
    private final CartService cartService;

    public ItemController(ItemService itemService, CartService cartService) {
        this.itemService = itemService;
        this.cartService = cartService;
    }

    @GetMapping
    public String findByFiltr(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "NO") String sort,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            Model model
    ){
        Page<ItemDto> paging = itemService.findByFiltr(search, sort, pageNumber, pageSize);
        List<ItemDto> content = new ArrayList<>(paging.getContent());

        int colCount = 3;
        int mod = content.size() % colCount;

        for (int i = 1; i <= mod; i++)
            content.add(new ItemDto(-1L));

        List<List<ItemDto>> items = IntStream.range(0, (content.size() + colCount - 1) / colCount)
                .mapToObj(i -> content.subList(i * colCount, Math.min((i + 1) * colCount, content.size())))
                .collect(Collectors.toList());

        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        model.addAttribute("paging", paging);
        model.addAttribute("items", items);

        return "items";
    }

    @GetMapping("/{id}")
    public String getItemById(@PathVariable Long id, Model model) {
        Optional<ItemDto> item = itemService.findById(id);
        if (item.isPresent()) {
            model.addAttribute("item", item.get());
            return "item";
        }

        return "redirect:/items";
    }

    @PostMapping
    public String doItemsAction(
            @RequestParam Long id,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "NO") String sort,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam String action
    ){
        cartService.changeCount(action, id);

        String redirectUrl = UriComponentsBuilder.fromPath("/items")
                .queryParam("search", search)
                .queryParam("sort", sort)
                .queryParam("pageNumber", pageNumber)
                .queryParam("pageSize", pageSize)
                .toUriString();

        return "redirect:" + redirectUrl;
    }

    @PostMapping("/{id}")
    public String doItemAction(@PathVariable Long id, @RequestParam String action) {
        cartService.changeCount(action, id);

        String redirectUrl = UriComponentsBuilder.fromPath("/items/{id}")
                .buildAndExpand(id)
                .toUriString();

        return "redirect:" + redirectUrl;
    }
}
