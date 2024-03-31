package edu.java.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "retry-type", havingValue = "constant")
public class ConstantRetryConfig {
    @Bean
    @Autowired
    public Retry retry(ApplicationConfig applicationConfig) {
        return Retry
            .fixedDelay(applicationConfig.retryAttempts(), applicationConfig.retryBackoff())
            .filter(throwable -> {
                if (throwable instanceof WebClientResponseException) {
                    HttpStatus statusCode = (HttpStatus) ((WebClientResponseException) throwable).getStatusCode();
                    return applicationConfig.retryHttpStatuses().contains(statusCode);
                }
                return false;
            });
    }
}
