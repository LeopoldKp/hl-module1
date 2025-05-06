package ru.hpclab.hl.module1.controller;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthCheckController implements HealthIndicator {

    private final JdbcTemplate jdbcTemplate;

    public HealthCheckController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    @Override
    public Health health() {
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return Health.up().withDetail("database", "available").build();
        } catch (Exception e) {
            return Health.down().withDetail("database", "unavailable").withException(e).build();
        }
    }

    @GetMapping("/readiness")
    public Health readiness() {
        return health();
    }

    @GetMapping("/liveness")
    public Health liveness() {
        return Health.up().build();
    }
}