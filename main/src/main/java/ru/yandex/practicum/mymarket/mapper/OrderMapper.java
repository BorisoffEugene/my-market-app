package ru.yandex.practicum.mymarket.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.mymarket.domain.Order;
import ru.yandex.practicum.mymarket.domain.OrderItem;
import ru.yandex.practicum.mymarket.dto.OrderDto;

import java.util.List;

@Component
public class OrderMapper {
    public OrderMapper() {
    }

    public OrderDto toDto(Order order, List<OrderItem> items) {
        return new OrderDto(order.getId(), order.getUsername(), items, order.getTotalSum());
    }

    public Order toEntity(OrderDto orderDto) {
        return new Order(orderDto.getId(), orderDto.getUsername(), orderDto.getTotalSum());
    }
}
