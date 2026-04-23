package ru.yandex.practicum.mymarket.integration.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.yandex.practicum.mymarket.config.RepositoryTestConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Import(RepositoryTestConfig.class)
@DisplayName("Интеграционное (WEB) тестирование страницы логина")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class LoginControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Логин")
    void testLogin() {
        webTestClient.get()
                .uri("/login")
                .exchange()
                .expectStatus().isOk();
    }
}
