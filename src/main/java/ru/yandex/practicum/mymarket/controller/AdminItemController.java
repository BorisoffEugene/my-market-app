package ru.yandex.practicum.mymarket.controller;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.mymarket.dto.ItemDto;
import ru.yandex.practicum.mymarket.service.ItemService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin-items")
public class AdminItemController {
    private final ItemService itemService;

    public AdminItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String findAll(Model model) {
        List<ItemDto> items = itemService.findAll(Sort.by("id"));
        model.addAttribute("items", items);
        return "admin-items";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("item", new ItemDto());
        return "admin-item";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("item") ItemDto item, @RequestParam("imageFile") MultipartFile file) {
        if (!file.isEmpty()) {
            byte[] bytes = null;
            item.setImgPath("/images/" + file.getOriginalFilename());
            try {
                bytes = file.getBytes();
                Path path = Paths.get("src/main/resources/static" + item.getImgPath());
                Files.write(path, bytes);

            } catch (IOException ignored) {
            }
        }

        itemService.save(item);
        return "redirect:/admin-items";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        itemService.deleteById(id);
        return "redirect:/admin-items";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Optional<ItemDto> item = itemService.findById(id);
        if (item.isPresent()) {
            model.addAttribute("item", item.get());
            return "admin-item";
        }

        return "redirect:/admin-items";
    }
}
