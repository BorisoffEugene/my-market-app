package ru.yandex.practicum.mymarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mymarket.domain.User;
import ru.yandex.practicum.mymarket.service.UserService;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Mono<Rendering> showForm() {
        return Mono.just(
                Rendering.view("register")
                        .modelAttribute("user", new User())
                        .build()
        );
    }

    @PostMapping
    public Mono<String> registerUser(@ModelAttribute("user") User user, Model model) {
        return userService.registerUser(user)
                .map(u -> "redirect:/login")
                .onErrorResume(e -> {
                    model.addAttribute("error", e.getMessage());
                    return Mono.just("register");
                });
    }
}
