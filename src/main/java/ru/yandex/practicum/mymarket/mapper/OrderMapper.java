package ru.yandex.practicum.mymarket.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.mymarket.domain.Order;
import ru.yandex.practicum.mymarket.dto.OrderDto;

import java.util.List;

@Component
public class OrderMapper {
    public OrderMapper() {
    }

    public OrderDto toDto(Order order) {
        return new OrderDto(order.getId(), order.getItems(), order.getTotalSum());
    }

    public Order toEntity(OrderDto orderDto) {
        return new Order(orderDto.getId(), orderDto.getItems(), orderDto.getTotalSum());
    }

    public List<OrderDto> toDtoList(List<Order> orders) {
        return orders.stream()
                .map(this::toDto)
                .toList();
    }

    public List<Order> toEntityList(List<OrderDto> ordersDto) {
        return ordersDto.stream()
                .map(this::toEntity)
                .toList();
    }
}
