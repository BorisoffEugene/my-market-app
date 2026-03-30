package ru.yandex.practicum.mymarket.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.mymarket.domain.Order;
import ru.yandex.practicum.mymarket.dto.OrderDto;
import ru.yandex.practicum.mymarket.mapper.OrderMapper;
import ru.yandex.practicum.mymarket.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public List<OrderDto> findAll() {
        return orderMapper.toDtoList(orderRepository.findAll());
    }

    public Optional<OrderDto> findById(Long id) {
        return Optional.of(orderMapper.toDto(orderRepository.findById(id).get()));
    }

    @Transactional
    public OrderDto save(OrderDto orderDto) {
        Order order = orderMapper.toEntity(orderDto);
        order.getItems().forEach(item -> item.setOrder(order));
        return orderMapper.toDto(orderRepository.save(order));
    }
}
