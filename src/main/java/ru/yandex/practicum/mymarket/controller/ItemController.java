package ru.yandex.practicum.mymarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.mymarket.domain.Item;
import ru.yandex.practicum.mymarket.service.ItemService;

import java.util.Optional;

@Controller
@RequestMapping({"/items", "/"})
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping
    public String findByFiltr(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "NO") String sort,
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            Model model
    ) {
        Page<Item> paging = itemService.findByFiltr(search, sort, pageNumber, pageSize);

        //todo paging to List<List<Item>> (по 3 в списке)

        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        model.addAttribute("paging", paging);

        return "items";
    }

    @GetMapping("/{id}")
    public String getItemById(@PathVariable Long id, Model model) {
        Optional<Item> item = itemService.findById(id);
        if (item.isPresent()) {
            model.addAttribute("item", item.get());
            return "item";
        }

        return "redirect:/items";
    }
}
