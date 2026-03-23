package ru.yandex.practicum.mymarket.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items", schema = "market")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    private int count;
    private Long price;
}
