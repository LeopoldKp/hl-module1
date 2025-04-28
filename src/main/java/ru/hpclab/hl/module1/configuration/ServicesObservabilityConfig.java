package ru.hpclab.hl.module1.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hpclab.hl.module1.service.statistics.ObservabilityService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ServicesObservabilityConfig {
    @Value("${observability.intervals:10,30,60}")
    private String intervalsProperty;

    @Value("${service.statistic.observability.delay:5000}")
    private int observabilityDelay;

    @Bean
    public List<Integer> intervals() {
        return Arrays.stream(intervalsProperty.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    @Bean
    @ConditionalOnProperty(prefix = "statistics", name = "observability", havingValue = "true")
    public ObservabilityService observabilityService(List<Integer> intervals) {
        return new ObservabilityService(intervals, observabilityDelay);
    }
}