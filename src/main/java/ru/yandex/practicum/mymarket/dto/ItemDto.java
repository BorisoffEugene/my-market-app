package ru.yandex.practicum.mymarket.dto;

import java.util.Objects;

public class ItemDto {
    private Long id;
    private String title;
    private String description;
    private String imgPath;
    private Long price;
    private int count;

    public ItemDto() {
    }

    public ItemDto(Long id, String title, String description, String imgPath, Long price, int count) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imgPath = imgPath;
        this.price = price;
        this.count = count;
    }

    public ItemDto(Long id) {
        this.id = id;
    }

    public ItemDto(String title, String description, String imgPath, Long price, int count) {
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
        ItemDto itemDto = (ItemDto) o;
        return count == itemDto.count && Objects.equals(id, itemDto.id) && Objects.equals(title, itemDto.title) && Objects.equals(description, itemDto.description) && Objects.equals(imgPath, itemDto.imgPath) && Objects.equals(price, itemDto.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, imgPath, price, count);
    }
}
