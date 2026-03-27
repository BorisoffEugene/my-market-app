package ru.yandex.practicum.mymarket.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders", schema = "market")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    private Long totalSum;

    public Order() {
    }

    public Order(List<OrderItem> items, Long totalSum) {
        this.items = items;
        this.totalSum = totalSum;
    }

    public Order(Long id, List<OrderItem> items, Long totalSum) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(items, order.items) && Objects.equals(totalSum, order.totalSum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, items, totalSum);
    }
}
