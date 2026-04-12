# Build stage
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build

WORKDIR /my-market-app

COPY pom.xml .
RUN mvn dependency:go-offline -B -q

COPY src src
RUN mvn clean package -DskipTests -B -q

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /my-market-app
COPY --from=build /my-market-app/payment-service/target/*.jar app.jar

RUN chown -R appuser:appgroup /app
USER appuser

EXPOSE 8081

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]