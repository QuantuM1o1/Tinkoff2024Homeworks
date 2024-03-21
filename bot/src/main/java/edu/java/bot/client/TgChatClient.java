package edu.java.bot.client;

import dto.ApiErrorResponse;
import edu.java.bot.configuration.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class TgChatClient {
    private final WebClient webClient;
    private final String url = "/tg-chat/{tgChatId}";

    @Autowired
    public TgChatClient(ApplicationConfig applicationConfig) {
        this.webClient = WebClient.builder()
            .baseUrl(applicationConfig.scrapperUrl())
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    public Mono<Void> deleteChat(Long tgChatId) {
        return this.webClient.method(HttpMethod.DELETE)
            .uri(url, tgChatId)
            .retrieve()
            .onStatus(
                HttpStatusCode::is4xxClientError,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .flatMap(errorResponse -> Mono.error(new Throwable(errorResponse.exceptionMessage())))
            )
            .bodyToMono(Void.class);
    }

    public Mono<Void> addChat(Long tgChatId) {
        return this.webClient.post()
            .uri(url, tgChatId)
            .retrieve()
            .onStatus(
                HttpStatusCode::is4xxClientError,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .flatMap(errorResponse -> Mono.error(new Throwable(errorResponse.exceptionMessage())))
            )
            .bodyToMono(Void.class);
    }
}
