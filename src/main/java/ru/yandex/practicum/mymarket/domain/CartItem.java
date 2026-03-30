package ru.yandex.practicum.mymarket.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "cart_items", schema = "market")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    private Long itemId;
    private int count;

    public CartItem() {
    }

    public CartItem(Cart cart, Long itemId) {
        this.cart = cart;
        this.itemId = itemId;
        this.count = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItem(Long itemId) {
        this.itemId = itemId;
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
        CartItem cartItem = (CartItem) o;
        return count == cartItem.count && Objects.equals(id, cartItem.id) && Objects.equals(cart, cartItem.cart) && Objects.equals(itemId, cartItem.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cart, itemId, count);
    }

    public void incCount() {
        this.count++;
    }

    public void decCount() {
        this.count--;
    }
}
