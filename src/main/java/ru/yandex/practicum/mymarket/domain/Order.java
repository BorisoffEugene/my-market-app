package ru.yandex.practicum.mymarket.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "orders", schema = "market")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    private Long totalSum;
}
