package edu.java.bot.client;

import dto.AddLinkRequest;
import dto.ApiErrorResponse;
import dto.LinkResponse;
import dto.ListLinksResponse;
import dto.RemoveLinkRequest;
import edu.java.bot.configuration.ApplicationConfig;
import exception.ChatIsNotFoundException;
import exception.IncorrectRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class LinksClient {
    private final WebClient webClient;
    private final String url = "/links";
    private final String header = "Tg-Chat-Id";

    @Autowired
    public LinksClient(ApplicationConfig applicationConfig) {
        this.webClient = WebClient.builder()
            .baseUrl(applicationConfig.scrapperUrl())
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    public Mono<LinkResponse> deleteLink(Long tgChatId, RemoveLinkRequest removeLinkRequest) {
        return this.webClient.method(HttpMethod.DELETE)
            .uri(this.url)
            .headers(headers -> headers.set(this.header, String.valueOf(tgChatId)))
            .body(BodyInserters.fromValue(removeLinkRequest))
            .retrieve()
            .onStatus(
                HttpStatus.NOT_FOUND::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .flatMap(apiErrorResponse ->
                        Mono.error(new ChatIsNotFoundException(apiErrorResponse.exceptionMessage())))
            )
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .flatMap(apiErrorResponse ->
                        Mono.error(new IncorrectRequestException(apiErrorResponse.exceptionMessage())))
            )
            .bodyToMono(LinkResponse.class);
    }

    public Mono<ListLinksResponse> getLinks(Long tgChatId) {
        return this.webClient.get()
            .uri(this.url)
            .headers(headers -> headers.set(this.header, String.valueOf(tgChatId)))
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .flatMap(apiErrorResponse ->
                        Mono.error(new IncorrectRequestException(apiErrorResponse.exceptionMessage())))
            )
            .bodyToMono(ListLinksResponse.class);
    }

    public Mono<LinkResponse> addLink(Long tgChatId, AddLinkRequest addLinkRequest) {
        return this.webClient.post()
            .uri(this.url)
            .headers(headers -> headers.set(this.header, String.valueOf(tgChatId)))
            .body(BodyInserters.fromValue(addLinkRequest))
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .flatMap(errorResponse ->
                        Mono.error(new IncorrectRequestException(errorResponse.exceptionMessage())))
            )
            .bodyToMono(LinkResponse.class);
    }
}
