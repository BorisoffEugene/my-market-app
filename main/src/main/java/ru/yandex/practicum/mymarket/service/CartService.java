package ru.yandex.practicum.mymarket.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mymarket.domain.Cart;
import ru.yandex.practicum.mymarket.domain.CartItem;
import ru.yandex.practicum.mymarket.dto.ItemDto;
import ru.yandex.practicum.mymarket.mapper.ItemMapper;
import ru.yandex.practicum.mymarket.repository.CartItemRepository;
import ru.yandex.practicum.mymarket.repository.CartRepository;

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

    @Cacheable(value = "cart_items", key = "'all'")
    public Flux<ItemDto> items() {
        return cartItemRepository.findCartItems().map(itemMapper::toDto);
    }

    @Cacheable(value = "cart_items", key = "'total'")
    public Mono<Long> total() {
        return cartRepository.cartTotal();
    }

    @CacheEvict(value = "cart_items", allEntries = true)
    public Mono<Void> changeCount(String action, Long id) {
        return cartRepository.findFirstByStatus("CURRENT")
                .switchIfEmpty(cartRepository.save(new Cart()))
                .flatMap(cart -> {
                    // Применяем к товару действия (PLUS, MINUS, DELETE)
                    switch (action) {
                        case "PLUS" ->
                                cartItemRepository.findByItemId(id)
                                        .switchIfEmpty(cartItemRepository.save(new CartItem(cart.getId(), id)))
                                        .flatMap(item -> {
                                            item.incCount();
                                            return cartItemRepository.save(item);
                                        });
                        case "MINUS" ->
                                cartItemRepository.findByItemId(id)
                                    .flatMap(item -> {
                                        item.decCount();
                                        if (item.getCount() == 0)
                                            return cartItemRepository.delete(item);
                                        else
                                            return cartItemRepository.save(item);
                                    });
                        case "DELETE" ->
                                cartItemRepository.findByItemId(id)
                                    .flatMap(cartItemRepository::delete);
                    }

                    // Считаем тотал и сохраняем
                    cart.setTotal(total().block());
                    if (cart.getTotal().equals(0L)) cart.setStatus("DELETED");
                    return cartRepository.save(cart);
                })
                .then();
    }

    public Mono<Void> sold() {
        return cartRepository.sold();
    }
}
