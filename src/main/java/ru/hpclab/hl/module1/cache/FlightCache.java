package ru.hpclab.hl.module1.cache;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.hpclab.hl.module1.dto.FlightDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class FlightCache {
    private final Map<Long, FlightDTO> cache = new HashMap<>();
    private final AtomicLong hits = new AtomicLong(0);
    private final AtomicLong misses = new AtomicLong(0);

    public FlightDTO get(Long flightId) {
        FlightDTO flight = cache.get(flightId);
        if (flight != null) {
            hits.incrementAndGet();
            return flight;
        }
        misses.incrementAndGet();
        return null;
    }

    public void put(Long flightId, FlightDTO flight) {
        cache.put(flightId, flight);
    }

    public void clear() {
        cache.clear();
    }

    public int size() {
        return cache.size();
    }

    @Scheduled(fixedRateString = "${cache.stats.print.interval:60000}") // По умолчанию 60 секунд
    public void printStats() {
        System.out.println("Flight Cache Stats:");
        System.out.println("Size: " + size());
        System.out.println("Hits: " + hits.get());
        System.out.println("Misses: " + misses.get());
        System.out.println("Hit rate: " +
                (hits.get() + misses.get() > 0 ?
                        (double) hits.get() / (hits.get() + misses.get()) : 0));
    }
}