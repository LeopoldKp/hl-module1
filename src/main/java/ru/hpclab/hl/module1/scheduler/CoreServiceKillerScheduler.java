package ru.hpclab.hl.module1.scheduler;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.hpclab.hl.module1.client.CoreServiceCrashClient;

@Slf4j
@Component
public class CoreServiceKillerScheduler {

    private final CoreServiceCrashClient crashClient;

    public CoreServiceKillerScheduler(CoreServiceCrashClient crashClient) {
        this.crashClient = crashClient;
    }

    // Исправленная аннотация (убрано "=20000" из placeholder)
    @Scheduled(fixedRateString = "${service.scheduler.core.crash.delay}")
    @Retry(name = "coreServiceRetry")
    public void killRandomCoreServicePod() {
        log.info("Attempting to crash a random Core Service pod");
        try {
            crashClient.callCrashEndpoint();
        } catch (Exception e) {
            log.error("Failed to crash Core Service pod: {}", e.getMessage());
            throw e;
        }
    }
}