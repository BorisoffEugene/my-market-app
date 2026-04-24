package ru.yandex.practicum.mymarket.controller;

import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mymarket.dto.ItemDto;
import ru.yandex.practicum.mymarket.service.ItemService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/admin-items")
public class AdminItemController {
    private final ItemService itemService;

    public AdminItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public Mono<Rendering> findAll() {
        return Mono.just(
                Rendering.view("admin-items")
                        .modelAttribute("items", itemService.findAll())
                        .build()
        );
    }

    @GetMapping("/add")
    public Mono<Rendering> add() {
        return Mono.just(
                Rendering.view("admin-item")
                        .modelAttribute("item", new ItemDto())
                        .build()
        );
    }

    @PostMapping("/save")
    public Mono<String> save(@ModelAttribute("item") ItemDto item, @RequestPart("imageFile") FilePart file) {
        return DataBufferUtils.join(file.content())
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    return bytes;
                })
                .flatMap(bytes -> {
                    item.setImgPath("/images/" + file.filename());
                    Path path = Paths.get("src/main/resources/static" + item.getImgPath());
                    try {
                        Files.write(path, bytes);
                    } catch (IOException ignored) {
                    }
                    return itemService.save(item);
                })
                .thenReturn("redirect:/admin-items");
    }

    @GetMapping("/delete/{id}")
    public Mono<String> delete(@PathVariable Long id) {
        return itemService.deleteById(id).thenReturn("redirect:/admin-items");
    }

    @GetMapping("/edit/{id}")
    public Mono<Rendering> edit(@PathVariable Long id) {
        return itemService.findById(id, null)
                .map(item -> Rendering.view("admin-item")
                        .modelAttribute("item", item)
                        .build())
                .switchIfEmpty(Mono.just(Rendering.redirectTo("/admin-items").build()));
    }
}
