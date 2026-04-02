package ru.yandex.practicum.mymarket.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mymarket.domain.Cart;
import ru.yandex.practicum.mymarket.domain.CartItem;
import ru.yandex.practicum.mymarket.dto.ItemDto;
import ru.yandex.practicum.mymarket.mapper.ItemMapper;
import ru.yandex.practicum.mymarket.repository.CartItemRepository;
import ru.yandex.practicum.mymarket.repository.CartRepository;

import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemMapper itemMapper;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, ItemMapper itemMapper) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.itemMapper = itemMapper;
    }

    public Flux<ItemDto> items() {
        return cartItemRepository.findCartItems().map(itemMapper::toDto);
    }

    public Mono<Long> total() {
        return cartRepository.cartTotal();
    }

    /*
    public void changeCount(String action, Long id) {
        Optional<Cart> optionalCart = cartRepository.findFirstByStatus("CURRENT");
        Cart cart = optionalCart.orElseGet(() -> cartRepository.save(new Cart()));

        Optional<CartItem> optionalCartItem = cartItemRepository.findByItemId(id);
        CartItem item;
        switch (action) {
            case "PLUS" -> {
                if (optionalCartItem.isEmpty()) {
                    item = new CartItem(cart, id);
                    cart.getItems().add(item);
                } else
                    item = optionalCartItem.get();

                item.incCount();
            }
            case "MINUS" -> {
                if (optionalCartItem.isPresent()) {
                    item = optionalCartItem.get();
                    item.decCount();
                    if (item.getCount() == 0)
                        cart.getItems().remove(item);
                }
            }
            case "DELETE" -> {
                if (optionalCartItem.isPresent()) {
                    item = optionalCartItem.get();
                    cart.getItems().remove(item);
                }
            }
        }

        if (cart.getItems().isEmpty())
            cart.setStatus("DELETED");

        cart.setTotal(total());
        cartRepository.save(cart);
    }
*/
    public void sold() {
        cartRepository.sold();
    }
}
