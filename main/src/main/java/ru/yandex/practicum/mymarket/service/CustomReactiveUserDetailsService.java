package ru.yandex.practicum.mymarket.service;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.userdetails.ReactiveUserDetailsPasswordService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mymarket.repository.UserRepository;

@Service
public class CustomReactiveUserDetailsService implements ReactiveUserDetailsService, ReactiveUserDetailsPasswordService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomReactiveUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> User.withUsername(user.getUsername())
                        .password(user.getPassword())
                        .authorities("USER")
                        .build()
                );
    }

    @Override
    public Mono<UserDetails> updatePassword(UserDetails user, String newPassword) {
        return userRepository.findByUsername(user.getUsername())
                .flatMap(userEntity -> {
                    userEntity.setPassword(passwordEncoder.encode(newPassword));
                    return userRepository.save(userEntity);
                })
                .cast(UserDetails.class);
    }
}