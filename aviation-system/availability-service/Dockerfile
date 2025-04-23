# Этап сборки
FROM gradle:8.6-jdk21 AS builder

WORKDIR /app

#COPY . .
COPY build.gradle .
COPY settings.gradle .
COPY src ./src

RUN gradle build --no-daemon

# Финальный образ
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]