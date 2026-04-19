package ru.yandex.practicum.mymarket.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.yandex.practicum.mymarket.domain.User;

public interface UserRepository extends ReactiveCrudRepository<User, String> {
}
