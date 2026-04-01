package ru.yandex.practicum.mymarket.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mymarket.dto.OrderDto;
import ru.yandex.practicum.mymarket.mapper.OrderMapper;
import ru.yandex.practicum.mymarket.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public Flux<OrderDto> findAll() {
        return orderRepository.findAll().map(orderMapper::toDto);
    }

    public Mono<OrderDto> findById(Long id) {
        return orderRepository.findById(id).map(orderMapper::toDto);
    }

    public Mono<OrderDto> save(OrderDto orderDto) {
        // todo хз что с itemsами в корзине?
        return orderRepository.save(orderMapper.toEntity(orderDto)).map(orderMapper::toDto);
    }
}
