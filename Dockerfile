FROM eclipse-temurin:21-jdk-alpine

WORKDIR /my-market-app
COPY . .
RUN ./mvnw clean package

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/my-market-app-1.0.jar"]