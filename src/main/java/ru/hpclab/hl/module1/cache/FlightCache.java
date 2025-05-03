package ru.hpclab.hl.module1.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.hpclab.hl.module1.dto.FlightDTO;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class FlightCache {
    private static final Logger logger = LoggerFactory.getLogger(FlightCache.class);
    private final RedisTemplate<String, FlightDTO> redisTemplate;
    private static final String CACHE_PREFIX = "flight:";
    private static final long TTL = 360; // 6 часов в минутах

    private final AtomicLong hits = new AtomicLong(0);
    private final AtomicLong misses = new AtomicLong(0);

    public FlightCache(
            @Qualifier("redisFlightTemplate") RedisTemplate<String, FlightDTO> redisTemplate
    ) {
        this.redisTemplate = redisTemplate;
    }

    public FlightDTO get(Long flightId) {
        try {
            String key = CACHE_PREFIX + flightId;
            FlightDTO flight = redisTemplate.opsForValue().get(key);

            if (flight != null) {
                hits.incrementAndGet();
                logger.info("Cache HIT for flight ID: {}", flightId);
                logger.debug("Retrieved from Redis - Key: {}, Value: {}", key, flight);
            } else {
                misses.incrementAndGet();
                logger.info("Cache MISS for flight ID: {}", flightId);
            }

            return flight;
        } catch (Exception e) {
            logger.error("Error getting flight {} from cache", flightId, e);
            misses.incrementAndGet();
            return null;
        }
    }

    public void put(Long flightId, FlightDTO flight) {
        try {
            String key = CACHE_PREFIX + flightId;
            logger.info("Caching flight ID: {}", flightId);
            logger.debug("Storing in Redis - Key: {}, Value: {}", key, flight);

            redisTemplate.opsForValue().set(
                    key,
                    flight,
                    TTL,
                    TimeUnit.MINUTES
            );
        } catch (Exception e) {
            logger.error("Error caching flight {}", flightId, e);
        }
    }

    public void clear() {
        try {
            logger.info("Clearing Redis flight cache");
            redisTemplate.execute((RedisCallback<Boolean>) connection -> {
                connection.flushDb();
                return true;
            });
        } catch (Exception e) {
            logger.error("Error clearing cache", e);
        }
    }

    @Scheduled(fixedRateString = "${cache.stats.print.interval:60000}")
    public void printStats() {
        try {
            Long size = redisTemplate.execute((RedisCallback<Long>) connection ->
                    connection.dbSize());

            long total = hits.get() + misses.get();
            double hitRate = total > 0 ? (hits.get() * 100.0 / total) : 0;

            logger.info("Flight Cache Stats:");
            logger.info("Redis keys count: {}", size);
            logger.info("Hits: {}", hits.get());
            logger.info("Misses: {}", misses.get());
            logger.info("Hit rate: {:.2f}%", hitRate);
        } catch (Exception e) {
            logger.error("Error printing cache stats", e);
        }
    }
}