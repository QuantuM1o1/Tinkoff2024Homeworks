package edu.java.configuration;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.util.retry.Retry;

@Configuration
public class GithubRetryConfig {
    @Autowired
    private RetryConfig retryConfig;

    @Autowired
    private GithubClientConfig githubClientConfig;

    @Bean
    public Retry githubRetry() {
        switch (this.githubClientConfig.retryPolicy().type()) {
            case RetryType.CONSTANT -> {
                return this.retryConfig.constantRetry(this.githubClientConfig.retryPolicy());
            }
            case RetryType.EXPONENTIAL -> {
                return this.retryConfig.exponentialRetry(this.githubClientConfig.retryPolicy());
            }
            case RetryType.LINEAR -> {
                return this.retryConfig.linearRetry(this.githubClientConfig.retryPolicy());
            }
            default -> {
                return Retry.fixedDelay(1, Duration.ZERO);
            }
        }
    }
}
