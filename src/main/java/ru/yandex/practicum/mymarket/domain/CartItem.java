package ru.yandex.practicum.mymarket.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;


@Table(name = "cart_items", schema = "market")
public class CartItem {
    @Id
    private Long id;
    private Long cartId;
    private Long itemId;
    private int count;

    public CartItem() {
    }

    public CartItem(Long cartId, Long itemId) {
        this.cartId = cartId;
        this.itemId = itemId;
        this.count = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
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
        return count == cartItem.count && Objects.equals(id, cartItem.id) && Objects.equals(cartId, cartItem.cartId) && Objects.equals(itemId, cartItem.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cartId, itemId, count);
    }

    public void incCount() {
        this.count++;
    }

    public void decCount() {
        this.count--;
    }
}
