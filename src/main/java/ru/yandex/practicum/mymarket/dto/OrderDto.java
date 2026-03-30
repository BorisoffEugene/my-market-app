package ru.yandex.practicum.mymarket.dto;

import ru.yandex.practicum.mymarket.domain.OrderItem;

import java.util.List;

public class OrderDto {
    private Long id;
    private List<OrderItem> items;
    private Long totalSum;

    public OrderDto(Long id, List<OrderItem> items, Long totalSum) {
        this.id = id;
        this.items = items;
        this.totalSum = totalSum;
    }

    public OrderDto(List<OrderItem> items, Long totalSum) {
        this.items = items;
        this.totalSum = totalSum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Long getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(Long totalSum) {
        this.totalSum = totalSum;
    }
}
