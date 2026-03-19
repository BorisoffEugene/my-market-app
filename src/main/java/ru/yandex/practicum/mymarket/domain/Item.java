package ru.yandex.practicum.mymarket.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "items", schema = "market")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String imgPath;
    private Long price;
}
