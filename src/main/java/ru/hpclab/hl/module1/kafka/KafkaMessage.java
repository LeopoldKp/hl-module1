package ru.hpclab.hl.module1.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class KafkaMessage {
    private EntityType entity;
    private OperationType operation;
    private JsonNode payload;
}