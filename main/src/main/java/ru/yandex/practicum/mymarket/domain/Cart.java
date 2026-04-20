package ru.yandex.practicum.mymarket.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table(name = "cart", schema = "market")
public class Cart {
    @Id
    private Long id;
    private String status;
    private String username;
    private Long total;

    public Cart(String username) {
        this.status = "CURRENT";
        this.username = username;
        this.total = 0L;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id) && Objects.equals(status, cart.status) && Objects.equals(username, cart.username) && Objects.equals(total, cart.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, username, total);
    }
}
