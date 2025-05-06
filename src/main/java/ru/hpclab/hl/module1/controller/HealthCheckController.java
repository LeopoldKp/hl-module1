package ru.hpclab.hl.module1.controller;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hpclab.hl.module1.client.CrudServiceClient;

@RestController
@RequestMapping("/health")
public class HealthCheckController implements HealthIndicator {

    private final RedisConnectionFactory redisConnectionFactory;
    private final CrudServiceClient crudServiceClient;

    public HealthCheckController(RedisConnectionFactory redisConnectionFactory,
                                 CrudServiceClient crudServiceClient) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.crudServiceClient = crudServiceClient;
    }

    @GetMapping
    @Override
    public Health health() {
        boolean redisOk = checkRedis();
        boolean crudServiceOk = checkCrudService();

        if (redisOk && crudServiceOk) {
            return Health.up()
                    .withDetail("redis", "available")
                    .withDetail("crudService", "available")
                    .build();
        } else {
            return Health.down()
                    .withDetail("redis", redisOk ? "available" : "unavailable")
                    .withDetail("crudService", crudServiceOk ? "available" : "unavailable")
                    .build();
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

    private boolean checkRedis() {
        try {
            return redisConnectionFactory.getConnection().ping() != null;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkCrudService() {
        try {
            crudServiceClient.getAllBookings();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}