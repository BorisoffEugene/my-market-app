package ru.yandex.practicum.mymarket.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mymarket.api.PaymentsApi;
import ru.yandex.practicum.mymarket.domain.DebitPostRequest;

@Service
public class PaymentService {
    private final PaymentsApi paymentsApi = new PaymentsApi();

    public Mono<String> check(Mono<Long> cartTotal) {
        return Mono.zip(paymentsApi.getBalnceWithHttpInfo(), cartTotal)
                .map(t -> {
                    HttpStatusCode status = t.getT1().getStatusCode();
                    if (status != HttpStatus.OK) return "Сервис не доступен";

                    float balance = t.getT1().getBody().getAvailableBalance();
                    Long total = t.getT2();

                    if (balance < total) return "Не достаточно средств";

                    return "OK";
                });
    }

    public Mono<String> debit(Mono<Long> cartTotal) {
        return cartTotal
                .map (total -> {
                    DebitPostRequest debitPostRequest = new DebitPostRequest();
                    debitPostRequest.setAmount((float) total);
                    return paymentsApi.debitPostWithHttpInfo(debitPostRequest)
                            .map(req -> switch (req.getStatusCode()) {
                                case HttpStatus.OK -> "OK";
                                case HttpStatus.PAYMENT_REQUIRED -> "Не достаточно средств";
                                default -> "Сервис не доступен";
                            });
                })
                .flatMap(res -> res);
    }
}
