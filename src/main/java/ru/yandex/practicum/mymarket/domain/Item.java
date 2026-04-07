package ru.yandex.practicum.mymarket.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table(name = "items", schema = "market")
public class Item {
    @Id
    private Long id;
    private String title;
    private String description;
    private String imgPath;
    private Long price;
    private int count;

    public Item() {
    }

    public Item(Long id) {
        this.id = id;
    }

    public Item(String title, String description, String imgPath, Long price, int count) {
        this.title = title;
        this.description = description;
        this.imgPath = imgPath;
        this.price = price;
        this.count = count;
    }

    public Item(Long id, String title, String description, String imgPath, Long price, int count) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imgPath = imgPath;
        this.price = price;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return count == item.count && Objects.equals(id, item.id) && Objects.equals(title, item.title) && Objects.equals(description, item.description) && Objects.equals(imgPath, item.imgPath) && Objects.equals(price, item.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, imgPath, price, count);
    }
}
