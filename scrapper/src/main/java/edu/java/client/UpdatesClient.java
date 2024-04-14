package edu.java.client;

import dto.ApiErrorResponse;
import dto.LinkUpdateRequest;
import edu.java.configuration.ApplicationConfig;
import exception.ChatIsNotFoundException;
import exception.IncorrectRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UpdatesClient {
    private final WebClient webClient;

    @Autowired
    public UpdatesClient(ApplicationConfig applicationConfig) {
        this.webClient = WebClient.builder()
            .baseUrl(applicationConfig.botUrl())
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    public Mono<Void> sendUpdate(LinkUpdateRequest linkUpdate) {
        return this.webClient.post()
            .uri("/updates")
            .body(BodyInserters.fromValue(linkUpdate))
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .flatMap(apiErrorResponse ->
                        Mono.error(new IncorrectRequestException(apiErrorResponse.exceptionMessage())))
            )
            .onStatus(
                HttpStatus.NOT_FOUND::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .flatMap(apiErrorResponse ->
                        Mono.error(new ChatIsNotFoundException(apiErrorResponse.exceptionMessage())))
            )
            .bodyToMono(Void.class);
    }
}
