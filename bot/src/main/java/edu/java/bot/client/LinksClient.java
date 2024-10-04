package edu.java.bot.client;

import dto.AddLinkRequest;
import dto.ApiErrorResponse;
import dto.LinkResponse;
import dto.ListLinksResponse;
import dto.RemoveLinkRequest;
import edu.java.bot.configuration.ApplicationConfig;
import exception.ChatIsNotFoundException;
import exception.IncorrectRequestException;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Component
public class LinksClient {
    private final WebClient webClient;

    private final String url = "/links";

    private final String header = "Tg-Chat-Id";

    private final Retry retry;

    @Autowired
    public LinksClient(ApplicationConfig applicationConfig, @Qualifier("scrapperRetry") Retry scrapperRetry) {
        this.webClient = WebClient.builder()
            .baseUrl(applicationConfig.scrapperUrl())
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
        this.retry = scrapperRetry;
    }

    public Mono<LinkResponse> deleteLink(Long tgChatId, RemoveLinkRequest removeLinkRequest) {
        return this.executeWithRetry(() ->
            this.webClient.method(HttpMethod.DELETE)
                .uri(url)
                .headers(headers -> headers.set(header, String.valueOf(tgChatId)))
                .body(BodyInserters.fromValue(removeLinkRequest))
                .retrieve()
                .onStatus(
                    HttpStatus.NOT_FOUND::equals,
                    clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                        .flatMap(apiErrorResponse ->
                            Mono.error(new ChatIsNotFoundException(apiErrorResponse.getExceptionMessage())))
                )
                .onStatus(
                    HttpStatus.BAD_REQUEST::equals,
                    clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                        .flatMap(apiErrorResponse ->
                            Mono.error(new IncorrectRequestException(apiErrorResponse.getExceptionMessage())))
                )
                .bodyToMono(LinkResponse.class));
    }

    public Mono<ListLinksResponse> getLinks(Long tgChatId) {
        return this.executeWithRetry(() ->
            this.webClient.get()
            .uri(this.url)
            .headers(headers -> headers.set(this.header, String.valueOf(tgChatId)))
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .flatMap(apiErrorResponse ->
                        Mono.error(new IncorrectRequestException(apiErrorResponse.getExceptionMessage())))
            )
            .bodyToMono(ListLinksResponse.class));
    }

    public Mono<LinkResponse> addLink(Long tgChatId, AddLinkRequest addLinkRequest) {
        return this.executeWithRetry(() ->
            this.webClient.post()
            .uri(this.url)
            .headers(headers -> headers.set(this.header, String.valueOf(tgChatId)))
            .body(BodyInserters.fromValue(addLinkRequest))
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .flatMap(errorResponse ->
                        Mono.error(new IncorrectRequestException(errorResponse.getExceptionMessage())))
            )
            .bodyToMono(LinkResponse.class));
    }

    private <T> Mono<T> executeWithRetry(Supplier<Mono<T>> supplier) {
        return Mono.defer(supplier).retryWhen(this.retry);
    }
}
