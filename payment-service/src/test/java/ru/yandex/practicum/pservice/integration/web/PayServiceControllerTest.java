package ru.yandex.practicum.pservice.integration.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.pservice.domain.DebitPost200Response;
import ru.yandex.practicum.pservice.domain.DebitPostRequest;
import ru.yandex.practicum.pservice.domain.GetBalnce200Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Интеграционное (WEB) тестирование платежного сервиса")
@TestMethodOrder(MethodOrderer.DisplayName.class)
@AutoConfigureWebTestClient
public class PayServiceControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    @WithMockUser(authorities = "SERVICE")
    @DisplayName("Проверка баланса")
    void testBalance() {
        GetBalnce200Response mockResponse = new GetBalnce200Response();
        mockResponse.setAvailableBalance(2_000F);

        webTestClient.get()
                .uri("/payment-service/balance")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GetBalnce200Response.class)
                .isEqualTo(mockResponse);
    }

    @Test
    @DisplayName("Проверка неавторизованного пользователя")
    void shouldReturn401_WhenNoTokenProvided() {
        webTestClient.get().uri("/payment-service/balance")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @WithMockUser(authorities = "SERVICE")
    @DisplayName("Проверка списания (достаточно средств)")
    void testDebit_Success() {
        DebitPostRequest debitPostRequest = new DebitPostRequest();
        debitPostRequest.setAmount(1_500F);

        DebitPost200Response mockResponse = new DebitPost200Response();
        mockResponse.setStatus("SUCCESS");

        webTestClient.post()
                .uri("/payment-service/debit")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(debitPostRequest), DebitPostRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(DebitPost200Response.class)
                .isEqualTo(mockResponse);
    }

    @Test
    @WithMockUser(authorities = "SERVICE")
    @DisplayName("Проверка списания (не достаточно средств)")
    void testDebit_402() {
        DebitPostRequest debitPostRequest = new DebitPostRequest();
        debitPostRequest.setAmount(2_500F);

        webTestClient.post()
                .uri("/payment-service/debit")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(debitPostRequest), DebitPostRequest.class)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.PAYMENT_REQUIRED);
    }
}
