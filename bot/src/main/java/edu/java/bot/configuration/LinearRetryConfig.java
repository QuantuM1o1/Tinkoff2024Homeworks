package edu.java.bot.configuration;

import java.time.Duration;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "retry-type", havingValue = "linear")
public class LinearRetryConfig {
    @Bean
    @Autowired
    public Retry retry(ApplicationConfig applicationConfig) {
        return new Retry() {
            @Override
            public Publisher<?> generateCompanion(Flux<RetrySignal> retrySignals) {
                return retrySignals.flatMap(this::getRetry);
            }

            private Mono<Long> getRetry(RetrySignal rs) {
                if (rs.totalRetries() < applicationConfig.retryAttempts()) {
                    long millis = applicationConfig.retryBackoff().toMillis();
                    millis *= rs.totalRetries();
                    Duration delay = Duration.ofMillis(millis);
                    return Mono.delay(delay)
                        .thenReturn(rs.totalRetries());
                } else {
                    throw Exceptions.propagate(rs.failure());
                }
            }
        };
    }
}
