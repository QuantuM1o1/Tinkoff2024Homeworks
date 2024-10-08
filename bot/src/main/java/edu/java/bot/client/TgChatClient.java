package edu.java.bot.client;

import dto.ApiErrorResponse;
import edu.java.bot.configuration.ApplicationConfig;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Component
public class TgChatClient {
    private final WebClient webClient;

    private final String url = "/tg-chat/{tgChatId}";

    private final Retry retry;

    @Autowired
    public TgChatClient(ApplicationConfig applicationConfig, @Qualifier("scrapperRetry") Retry scrapperRetry) {
        this.webClient = WebClient.builder()
            .baseUrl(applicationConfig.scrapperUrl())
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
        this.retry = scrapperRetry;
    }

    public Mono<Void> deleteChat(Long tgChatId) {
        return this.executeWithRetry(() ->
            this.webClient.method(HttpMethod.DELETE)
                .uri(url, tgChatId)
                .retrieve()
                .onStatus(
                    HttpStatusCode::isError,
                    response -> response.bodyToMono(ApiErrorResponse.class)
                )
                .bodyToMono(Void.class));
    }

    public Mono<Void> addChat(Long tgChatId) {
        return this.executeWithRetry(() ->
            this.webClient.post()
                .uri(url, tgChatId)
                .retrieve()
                .onStatus(
                    HttpStatusCode::isError,
                    response -> response.bodyToMono(ApiErrorResponse.class)
                )
                .bodyToMono(Void.class));
    }

    private <T> Mono<T> executeWithRetry(Supplier<Mono<T>> supplier) {
        return Mono.defer(supplier).retryWhen(this.retry);
    }
}
