package ru.yandex.practicum.mymarket.integration.db;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.r2dbc.test.autoconfigure.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.test.StepVerifier;
import ru.yandex.practicum.mymarket.config.RepositoryTestConfig;
import ru.yandex.practicum.mymarket.domain.User;
import ru.yandex.practicum.mymarket.repository.UserRepository;

@DataR2dbcTest
@Import(RepositoryTestConfig.class)
@DisplayName("Интеграционное (DB) тестирование пользователей")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private User user1;

    @BeforeEach
    void beforeEach() {
        userRepository.deleteAll().block();
        user1 = userRepository.save(new User("user", passwordEncoder.encode("password"))).block();
    }

    @Test
    @DisplayName("Получение пользователя (пользователь есть)")
    void testFindByUsername_Success() {
        StepVerifier.create(userRepository.findByUsername(user1.getUsername()))
                .expectNextMatches(user -> user.getUsername().equals(user1.getUsername()))
                .verifyComplete();
    }

    @Test
    @DisplayName("Получение пользователя (пользователя нет)")
    void testFindByUsername_NotFound() {
        StepVerifier.create(userRepository.findByUsername("admin"))
                .expectNextCount(0)
                .verifyComplete();
    }
}
