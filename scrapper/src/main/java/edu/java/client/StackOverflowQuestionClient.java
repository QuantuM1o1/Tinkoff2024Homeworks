package edu.java.client;

import edu.java.configuration.ApplicationConfig;
import edu.java.dto.StackOverflowQuestionRequest;
import edu.java.dto.StackOverflowQuestionResponse;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Component
public class StackOverflowQuestionClient
    implements AsyncClient<StackOverflowQuestionResponse, StackOverflowQuestionRequest> {
    private final org.springframework.web.reactive.function.client.WebClient webClient;
    private final Retry retry;

    @Autowired
    public StackOverflowQuestionClient(ApplicationConfig applicationConfig, Retry retry) {
        this.webClient = WebClient
            .builder()
            .baseUrl(applicationConfig.stackOverflowBaseUrl())
            .build();
        this.retry = retry;
    }

    @Override
    public Mono<StackOverflowQuestionResponse> fetch(StackOverflowQuestionRequest request) {
        return this.executeWithRetry(() ->
            this.webClient
            .get()
            .uri("/questions/{questionId}?site={site}", request.questionId(), request.site())
            .retrieve()
            .bodyToMono(StackOverflowQuestionResponse.class));
    }

    private <T> Mono<T> executeWithRetry(Supplier<Mono<T>> supplier) {
        return Mono.defer(supplier).retryWhen(this.retry);
    }
}
