package ru.yandex.practicum.pservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.pservice.api.BalanceApi;
import ru.yandex.practicum.pservice.api.DebitApi;
import ru.yandex.practicum.pservice.domain.DebitPost200Response;
import ru.yandex.practicum.pservice.domain.DebitPostRequest;
import ru.yandex.practicum.pservice.domain.GetBalnce200Response;

@RestController
@RequestMapping("/payment-service")
public class PayServiceController implements BalanceApi, DebitApi {
    @Override
    public Mono<ResponseEntity<GetBalnce200Response>> getBalnce(ServerWebExchange exchange) {
        GetBalnce200Response balance = new GetBalnce200Response();
        balance.setAvailableBalance(2_000F);

        return Mono.just(ResponseEntity.ok(balance));
    }

    @Override
    public Mono<ResponseEntity<DebitPost200Response>> debitPost(Mono<DebitPostRequest> debitPostRequest, ServerWebExchange exchange) {
        return Mono.zip(getBalnce(exchange), debitPostRequest)
                .map(t -> {
                    if (t.getT1().getBody().getAvailableBalance() >= t.getT2().getAmount()) {
                        DebitPost200Response response = new DebitPost200Response();
                        response.setStatus("SUCCESS");
                        return ResponseEntity.ok(response);
                    } else
                        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).build();
                });
    }
}
