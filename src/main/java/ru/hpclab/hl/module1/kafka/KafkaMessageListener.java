package ru.hpclab.hl.module1.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessageListener {
    private final ObjectMapper objectMapper;
    private final KafkaMessageDispatcher dispatcher;

    @KafkaListener(
            topics = "${KAFKA_TOPIC:aviation-events}",  // Читаем из топика (с дефолтным значением)
            groupId = "${KAFKA_GROUP_ID:aviation-consumer-group}",  // Группа потребителей
            concurrency = "${KAFKA_CONCURRENCY:3}",  // 3 потока обработки
            containerFactory = "batchFactory"  // Используем batch-обработку
    )
    public void listen(List<String> messages) {  // Получаем список сообщений
        for (String message : messages) {  // Обрабатываем каждое сообщение
            try {
                // 1. Преобразуем JSON в объект KafkaMessage
                KafkaMessage kafkaMessage = objectMapper.readValue(message, KafkaMessage.class);

                // 2. Логируем полученное сообщение
                log.info("Received Kafka message: {}", kafkaMessage);

                // 3. Передаём сообщение на обработку
                dispatcher.dispatch(kafkaMessage);

            } catch (Exception e) {
                // Обработка ошибок
                log.error("Error processing Kafka message: {}", message, e);
            }
        }
    }
}