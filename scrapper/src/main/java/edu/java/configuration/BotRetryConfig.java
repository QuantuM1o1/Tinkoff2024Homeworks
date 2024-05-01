package edu.java.configuration;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.util.retry.Retry;

@Configuration
public class BotRetryConfig {
    @Autowired
    private RetryConfig retryConfig;

    @Autowired
    private ApplicationConfig applicationConfig;

    @Bean
    public Retry botRetry() {
        switch (this.applicationConfig.retryPolicy().type()) {
            case RetryType.CONSTANT -> {
                return this.retryConfig.constantRetry(this.applicationConfig.retryPolicy());
            }
            case RetryType.EXPONENTIAL -> {
                return this.retryConfig.exponentialRetry(this.applicationConfig.retryPolicy());
            }
            case RetryType.LINEAR -> {
                return this.retryConfig.linearRetry(this.applicationConfig.retryPolicy());
            }
            default -> {
                return Retry.fixedDelay(1, Duration.ZERO);
            }
        }
    }
}
