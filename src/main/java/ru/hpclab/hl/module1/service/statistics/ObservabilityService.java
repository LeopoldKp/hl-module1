package ru.hpclab.hl.module1.service.statistics;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

@Service
public class ObservabilityService {
    private static final Instant PENDING_STOP = null;
    private final List<Integer> intervals;
    private final int delay;
    private final Set<Timing> timings = new ConcurrentSkipListSet<>(Comparator.comparing(Timing::getStart));

    public ObservabilityService(List<Integer> intervals, int delay) {
        this.intervals = intervals;
        this.delay = delay;
    }

    public void start(String name) {
        Timing timing = new Timing(name, Instant.now(), PENDING_STOP);
        timings.add(timing);
    }

    public void stop(String name) {
        Instant stopTime = Instant.now();
        Optional<Timing> timingOpt = timings.stream()
                .filter(t -> t.getName().equals(name) && t.getStop() == PENDING_STOP)
                .findFirst();

        if (timingOpt.isPresent()) {
            Timing timing = timingOpt.get();
            timing.setStop(stopTime);
        }
    }

    public void recordCustomMetric(String metricName, double value) {
        System.out.println("Custom metric: " + metricName + " = " + value);
    }

    @Async
    @Scheduled(fixedDelayString = "${service.statistic.observability.delay}")
    public void getStatistics() {
        List<Timing> snapshot = new ArrayList<>(timings);
        Instant now = Instant.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                .withZone(ZoneId.systemDefault());

        int maxInterval = intervals.stream().max(Integer::compare).orElse(0);
        timings.removeIf(timing -> now.minusSeconds(maxInterval).isAfter(timing.getStart()));

        Set<String> uniqueNames = snapshot.stream().map(Timing::getName).collect(Collectors.toSet());
        Map<String, Map<Integer, Double>> statisticsMap = new HashMap<>();

        for (String name : uniqueNames) {
            Map<Integer, Double> intervalStats = new HashMap<>();

            for (int interval : intervals) {
                List<Timing> filteredTimings = snapshot.stream()
                        .filter(t -> t.getStop() != PENDING_STOP
                                && !t.getStart().isBefore(now.minusSeconds(interval))
                                && !t.getStart().isAfter(now)
                                && t.getName().equals(name))
                        .toList();

                if (!filteredTimings.isEmpty()) {
                    double averageDuration = filteredTimings.stream()
                            .mapToLong(t -> Duration.between(t.getStart(), t.getStop()).toMillis())
                            .average().orElse(0.0);

                    intervalStats.put(interval, averageDuration / 1000);
                }
            }

            if (!intervalStats.isEmpty()) {
                statisticsMap.put(name, intervalStats);
            }
        }

        System.out.println("\n--- Observability statistics (delay = " + delay + " ms) ---");
        String timestamp = "[" + formatter.format(now) + "]";

        for (Map.Entry<String, Map<Integer, Double>> entry : statisticsMap.entrySet()) {
            String name = entry.getKey();
            for (Map.Entry<Integer, Double> intervalEntry : entry.getValue().entrySet()) {
                int interval = intervalEntry.getKey();
                double avgDuration = intervalEntry.getValue();
                System.out.println(timestamp + " - " + interval + "s : " + name + " - " + avgDuration + " s.");
            }
        }
    }
}