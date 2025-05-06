package ru.hpclab.hl.module1.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(CircuitBreakerRegistry registry, MeterRegistry meterRegistry) {
        CircuitBreaker circuitBreaker = registry.circuitBreaker("coreService");

        return WebClient.builder()
                .baseUrl("http://aviation-main-internal:8080")
                .filter(ExchangeFilterFunction.ofRequestProcessor(
                        clientRequest -> {
                            Timer.Sample sample = Timer.start(meterRegistry);
                            return Mono.just(clientRequest)
                                    .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                                    .doOnTerminate(() -> sample.stop(Timer.builder("core.service.call.timer")
                                            .description("Timer for Core Service calls")
                                            .register(meterRegistry)));
                        }
                ))
                .build();
    }
}