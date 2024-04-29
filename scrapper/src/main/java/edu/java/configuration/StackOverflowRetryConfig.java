package edu.java.configuration;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.util.retry.Retry;

@Configuration
public class StackOverflowRetryConfig {
    @Autowired
    private RetryConfig retryConfig;

    @Autowired
    private StackOverflowClientConfig stackOverflowClientConfig;

    @Bean
    public Retry stackoverflowRetry() {
        switch (this.stackOverflowClientConfig.retryPolicy().type()) {
            case RetryType.CONSTANT -> {
                return this.retryConfig.constantRetry(this.stackOverflowClientConfig.retryPolicy());
            }
            case RetryType.EXPONENTIAL -> {
                return this.retryConfig.exponentialRetry(this.stackOverflowClientConfig.retryPolicy());
            }
            case RetryType.LINEAR -> {
                return this.retryConfig.linearRetry(this.stackOverflowClientConfig.retryPolicy());
            }
            default -> {
                return Retry.fixedDelay(1, Duration.ZERO);
            }
        }
    }
}
