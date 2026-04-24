# Яндекс практикум. Мидл Java-разработчик. Модуль 2. Спринт 8

Веб-приложение "Витрина интернет-магазина".
В восьмом спринте добавляем авторизацию через keycloak

## Описание
Этот проект демонстрирует умение работать с
* Spring Boot Framework
* Spring Boot Test
* Spring WebFlux
* Spring Data R2DBC
* системой сборки Maven
* git-репозиторием
* базами данных, в том числе встроенными (H2)
* NoSQL базами данных для кэширования (Redis)
* RESTful-сервисами
* OpenAPI
* модульным и интеграционным тестированием средствами Spring Boot Test + WebTestClient + JUnit5 + мокирование
* паттерном MVC
* докером
* встроенным контейнером сервлетов Netty
* шаблонами HTML-страниц для Thymeleaf
* Spring Security
* Keycloak

## Функционал
* Витрина магазина
* Админ страница для заполнения товара
* Карточка товара
* Корзина
* Список заказов
* Карточка заказа

## Требования
* Java JDK 21
* Maven
* PostgreSQL 18
* Redis
* Docker
* Keycloack

## Технологии
* Java 21
* Spring Boot
* Spring WebFlux
* Spring Data R2DBC
* Thymeleaf
* Spring Boot Test
* WebTestClient
* JUnit5
* Mockito
* Maven
* PostgreSQL
* H2
* Redis
* OpenAPI
* OAuth2
* Spring Security
* Keycloak

## Установка и запуск
1. **Клонируйте репозиторий:**
    ```bash
    git clone https://github.com/BorisoffEugene/my-market-app
    ```
2. **Перейдите в папку проекта:**
    ```bash
    cd my-market-app
    ```
3. **Соберите проект с помощью Maven:**
    ```bash
    mvn clean package
    ```
4. **Запустите тесты с помощью Maven:**
   ```bash
   mvn test
   ```
5. **Запуск Redis c помощью Docker:**
   ```bash
   docker run --name redis-server -it --rm -p 6379:6379 redis:7.4.2-bookworm sh -c "redis-server && sleep 7 && redis-cli"
   ```
6. **Запуск keycloack с помощью Docker:**
7. ```bash
   docker run -d -p 8082:8082 --name keycloak \ 
   -e KC_BOOTSTRAP_ADMIN_USERNAME=admin \
   -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin \
   quay.io/keycloak/keycloak:26.1.3 start-dev
   ```
8. **Запустите проект из командной строки:**
    ```bash
    java -jar target/my-market-app-1.0.jar 
    ```
9. **Админ страница управления товаром:**
    ```text
    http://localhost/admin-items
    ```
10. **Витрина товаров:**
    ```text
    http://localhost
    ```
11. **Также запустить приложение можно из докера:**
    ```bash
   docker-compose up --build
    ```

## Автор
**Борисов Евгений**