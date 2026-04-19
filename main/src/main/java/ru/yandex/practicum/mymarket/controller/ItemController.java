package ru.yandex.practicum.mymarket.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mymarket.dto.ItemDto;
import ru.yandex.practicum.mymarket.service.CartService;
import ru.yandex.practicum.mymarket.service.ItemService;

import java.util.ArrayList;
import java.util.List;
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
    public Mono<Rendering> findByFiltr(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "NO") String sort,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize
    ){
        return itemService.findByFiltr(search, sort, pageNumber, pageSize)
                .flatMap(paging -> {
                    List<ItemDto> content = new ArrayList<>(paging.getContent());

                    int colCount = 3;
                    int mod = content.size() % colCount;

                    for (int i = 1; i <= mod; i++)
                        content.add(new ItemDto(-1L));

                    List<List<ItemDto>> items = IntStream.range(0, (content.size() + colCount - 1) / colCount)
                            .mapToObj(i -> content.subList(i * colCount, Math.min((i + 1) * colCount, content.size())))
                            .collect(Collectors.toList());

                    return ReactiveSecurityContextHolder.getContext()
                            .map(SecurityContext::getAuthentication)
                            .map(Authentication::isAuthenticated)
                            .defaultIfEmpty(false)
                            .map(isAuthenticated -> Rendering.view("items")
                                    .modelAttribute("search", search)
                                    .modelAttribute("sort", sort)
                                    .modelAttribute("paging", paging)
                                    .modelAttribute("items", items)
                                    .modelAttribute("isAuthenticated", isAuthenticated)
                                    .build()
                            );
                });
    }

    @GetMapping("/{id}")
    public Mono<Rendering> getItemById(@PathVariable Long id) {
        return itemService.findById(id)
                .flatMap(item -> ReactiveSecurityContextHolder.getContext()
                        .map(SecurityContext::getAuthentication)
                        .map(Authentication::isAuthenticated)
                        .defaultIfEmpty(false)
                        .map(isAuthenticated -> Rendering.view("item")
                                .modelAttribute("item", item)
                                .modelAttribute("isAuthenticated", isAuthenticated)
                                .build()
                        )
                        .switchIfEmpty(Mono.just(Rendering.redirectTo("/items").build()))
                );
    }

    @PostMapping
    public Mono<String> doItemsAction(
            @RequestParam Long id,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "NO") String sort,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam String action
    ){
        String redirectUrl = UriComponentsBuilder.fromPath("/items")
                .queryParam("search", search)
                .queryParam("sort", sort)
                .queryParam("pageNumber", pageNumber)
                .queryParam("pageSize", pageSize)
                .toUriString();

        return cartService.changeCount(action, id).thenReturn("redirect:" + redirectUrl);
    }

    @PostMapping("/{id}")
    public Mono<String> doItemAction(@PathVariable Long id, @RequestParam String action) {
        return cartService.changeCount(action, id).thenReturn("redirect:/items/" + id);
    }
}
