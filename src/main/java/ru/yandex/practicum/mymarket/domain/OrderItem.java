package ru.yandex.practicum.mymarket.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "order_items", schema = "market")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    private String title;
    private int count;
    private Long price;

    public OrderItem() {
    }

    public OrderItem(String title, int count, Long price) {
        this.title = title;
        this.count = count;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return count == orderItem.count && Objects.equals(id, orderItem.id) && Objects.equals(order, orderItem.order) && Objects.equals(title, orderItem.title) && Objects.equals(price, orderItem.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order, title, count, price);
    }
}
