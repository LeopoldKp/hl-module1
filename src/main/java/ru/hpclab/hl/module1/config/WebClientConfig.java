package ru.hpclab.hl.module1.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(CircuitBreakerRegistry registry) {
        CircuitBreaker circuitBreaker = registry.circuitBreaker("coreServiceCircuitBreaker");

        ExchangeFilterFunction filter = (request, next) -> {
            Mono<ClientResponse> response = next.exchange(request);
            return response.transformDeferred(CircuitBreakerOperator.of(circuitBreaker));
        };

        return WebClient.builder()
                .baseUrl("http://aviation-main-internal:8080")
                .filter(filter)
                .build();
    }
}