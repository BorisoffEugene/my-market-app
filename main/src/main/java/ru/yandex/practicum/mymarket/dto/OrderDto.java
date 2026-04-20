package ru.yandex.practicum.mymarket.dto;

import ru.yandex.practicum.mymarket.domain.OrderItem;

import java.util.List;
import java.util.Objects;

public class OrderDto {
    private Long id;
    private String username;
    private List<OrderItem> items;
    private Long totalSum;

    public OrderDto(Long id, List<OrderItem> items, Long totalSum) {
        this.id = id;
        this.items = items;
        this.totalSum = totalSum;
    }

    public OrderDto(Long id, String username, List<OrderItem> items, Long totalSum) {
        this.id = id;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return Objects.equals(id, orderDto.id) && Objects.equals(username, orderDto.username) && Objects.equals(items, orderDto.items) && Objects.equals(totalSum, orderDto.totalSum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, items, totalSum);
    }
}
