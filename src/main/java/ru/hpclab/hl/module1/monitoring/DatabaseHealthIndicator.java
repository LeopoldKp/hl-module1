package ru.hpclab.hl.module1.monitoring;

import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class DatabaseHealthIndicator extends AbstractHealthIndicator {
    private final JdbcTemplate jdbcTemplate;

    public DatabaseHealthIndicator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        // Проверка БД
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            builder.up().withDetail("database", "Available");
        } catch (Exception e) {
            builder.down(e).withDetail("database", "Unavailable");
        }
    }
}