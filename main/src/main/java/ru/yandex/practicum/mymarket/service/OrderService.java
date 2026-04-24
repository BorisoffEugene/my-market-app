package ru.yandex.practicum.mymarket.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mymarket.domain.Order;
import ru.yandex.practicum.mymarket.dto.OrderDto;
import ru.yandex.practicum.mymarket.mapper.OrderMapper;
import ru.yandex.practicum.mymarket.repository.CartRepository;
import ru.yandex.practicum.mymarket.repository.OrderItemRepository;
import ru.yandex.practicum.mymarket.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, CartRepository cartRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.orderMapper = orderMapper;
    }

    public Flux<OrderDto> findAll(String username) {
        return orderRepository.findAllByUsername(username)
                .flatMap(order ->
                    orderItemRepository.findAllByOrderId(order.getId())
                            .collectList()
                            .map(items -> orderMapper.toDto(order, items))
                );
    }

    public Mono<OrderDto> findById(Long id) {
        return orderRepository.findById(id)
                .flatMap(order ->
                        orderItemRepository.findAllByOrderId(order.getId())
                                .collectList()
                                .map(items -> orderMapper.toDto(order, items))
                );
    }

    @Caching(evict = {
            @CacheEvict(value = "cart_items", key = "'all:' + #username"),
            @CacheEvict(value = "cart_items", key = "'total:' + #username")
    })
    public Mono<OrderDto> sold(String username) {
        return cartRepository.cartTotal(username)
                        .flatMap(total -> orderRepository.save(new Order(username, total))
                                .flatMap(order -> orderItemRepository.sold(order.getId())
                                        .then(cartRepository.sold(username))
                                        .then(findById(order.getId()))));
    }
}
