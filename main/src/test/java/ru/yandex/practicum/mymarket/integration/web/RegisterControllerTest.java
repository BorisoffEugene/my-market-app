package ru.yandex.practicum.mymarket.integration.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mymarket.config.RepositoryTestConfig;
import ru.yandex.practicum.mymarket.domain.User;
import ru.yandex.practicum.mymarket.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Import(RepositoryTestConfig.class)
@DisplayName("Интеграционное (WEB) тестирование страницы регистрации")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class RegisterControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("Форма регистрации")
    void testShowForm() {
        webTestClient.get()
                .uri("/register")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("Зарегистрировать")
    void testRegister() {
        User mockUser = new User("user", "password");
        when(userService.registerUser(any(User.class))).thenReturn(Mono.just(mockUser));

        webTestClient.post()
                .uri("/register")
                .exchange()
                .expectStatus().isOk();
    }
}
