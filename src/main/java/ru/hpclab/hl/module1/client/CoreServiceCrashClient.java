package ru.hpclab.hl.module1.client;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CoreServiceCrashClient {
    private final WebClient webClient;

    public CoreServiceCrashClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://aviation-main-internal:8080").build();
    }

    @Retry(name = "coreServiceRetry")
    public void callCrashEndpoint() {
        webClient.post()
                .uri("/internal/crash")
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}