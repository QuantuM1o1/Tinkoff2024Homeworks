package edu.java.configuration;

import java.time.Duration;
import org.reactivestreams.Publisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Configuration
public class RetryConfig {
    public Retry constantRetry(RetryPolicy retryPolicy) {
        return Retry
            .fixedDelay(retryPolicy.attempts(), retryPolicy.backoff())
            .filter(throwable -> {
                if (throwable instanceof WebClientResponseException) {
                    int statusCode = ((WebClientResponseException) throwable).getStatusCode().value();
                    return retryPolicy.httpStatusCodes().contains(statusCode);
                }
                return false;
            });
    }

    public Retry exponentialRetry(RetryPolicy retryPolicy) {
        return Retry
            .backoff(retryPolicy.attempts(), retryPolicy.backoff())
            .filter(throwable -> {
                if (throwable instanceof WebClientResponseException) {
                    int statusCode = ((WebClientResponseException) throwable).getStatusCode().value();
                    return retryPolicy.httpStatusCodes().contains(statusCode);
                }
                return false;
            });
    }

    public Retry linearRetry(RetryPolicy retryPolicy) {
        return new Retry() {
            @Override
            public Publisher<?> generateCompanion(Flux<RetrySignal> retrySignals) {
                return retrySignals.flatMap(this::getRetry);
            }

            private Mono<Long> getRetry(RetrySignal rs) {
                if (rs.totalRetries() < retryPolicy.attempts()) {
                    long millis = retryPolicy.backoff().toMillis();
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
