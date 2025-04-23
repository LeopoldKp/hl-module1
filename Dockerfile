# Используем образ с JDK и Gradle для сборки
FROM gradle:8.6-jdk21 AS builder

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем только файлы, необходимые для сборки (для лучшего кэширования)
COPY build.gradle .
COPY settings.gradle .
COPY src ./src

# Собираем приложение
RUN gradle build --no-daemon

# Финальный образ с приложением
FROM openjdk:21-jdk-slim

WORKDIR /app

# Копируем собранный JAR из стадии builder
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]