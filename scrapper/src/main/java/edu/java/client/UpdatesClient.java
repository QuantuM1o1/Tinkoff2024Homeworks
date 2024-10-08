package edu.java.client;

import dto.ApiErrorResponse;
import dto.LinkUpdateRequest;
import edu.java.configuration.ApplicationConfig;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Component
public class UpdatesClient {
    private final WebClient webClient;
    private final Retry retry;

    @Autowired
    public UpdatesClient(ApplicationConfig applicationConfig, @Qualifier("botRetry") Retry retry) {
        this.webClient = WebClient.builder()
            .baseUrl(applicationConfig.botUrl())
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
        this.retry = retry;
    }

    public Mono<Void> sendUpdate(LinkUpdateRequest linkUpdate) {
        return this.executeWithRetry(() ->
            this.webClient.post()
                .uri("/updates")
                .body(BodyInserters.fromValue(linkUpdate))
                .retrieve()
                .onStatus(
                    HttpStatusCode::isError,
                    clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                )
                .bodyToMono(Void.class));
    }

    private <T> Mono<T> executeWithRetry(Supplier<Mono<T>> supplier) {
        return Mono.defer(supplier).retryWhen(this.retry);
    }
}
