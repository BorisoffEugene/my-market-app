package ru.yandex.practicum.mymarket.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table(name = "orders", schema = "market")
public class Order {
    @Id
    private Long id;
    private String username;
    private Long totalSum;

    public Order() {
    }

    public Order(Long totalSum) {
        this.totalSum = totalSum;
    }

    public Order(Long id, Long totalSum) {
        this.id = id;
        this.totalSum = totalSum;
    }

    public Order(Long id, String username, Long totalSum) {
        this.id = id;
        this.username = username;
        this.totalSum = totalSum;
    }

    public Order(String username, Long totalSum) {
        this.username = username;
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

    public Long getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(Long totalSum) {
        this.totalSum = totalSum;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(username, order.username) && Objects.equals(totalSum, order.totalSum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, totalSum);
    }
}
