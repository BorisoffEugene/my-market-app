# Яндекс практикум. Мидл Java-разработчик. Модуль 2. Спринт 7

Веб-приложение "Витрина интернет-магазина".
В седьмом спринте добавляем Redis как кэш.
Также добавляем RESTful-сервис платежей.

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
5. **Запустите проект из командной строки:**
    ```bash
    java -jar target/my-market-app-1.0.jar 
    ```
6. **Админ страница управления товаром:**
    ```text
    http://localhost/admin-items
    ```
7. **Витрина товаров:**
    ```text
    http://localhost
    ```
8. **Также запустить приложение можно из докера:**
    ```bash
   docker-compose up --build
    ```

## Автор
**Борисов Евгений**