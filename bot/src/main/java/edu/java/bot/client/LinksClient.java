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
import org.springframework.http.HttpStatusCode;
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
            .uri(url)
            .headers(headers -> headers.set(header, String.valueOf(tgChatId)))
            .body(BodyInserters.fromValue(removeLinkRequest))
            .retrieve()
            .onStatus(
                HttpStatusCode::is4xxClientError,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .flatMap(errorResponse -> {
                        if (errorResponse.code().equals("404")) {
                            return Mono.error(new ChatIsNotFoundException(errorResponse.exceptionMessage()));
                        } else {
                            return Mono.error(new IncorrectRequestException(errorResponse.exceptionMessage()));
                        }
                    })
            )
            .bodyToMono(LinkResponse.class);
    }

    public Mono<ListLinksResponse> getLinks(Long tgChatId) {
        return this.webClient.get()
            .uri(url)
            .headers(headers -> headers.set(header, String.valueOf(tgChatId)))
            .retrieve()
            .onStatus(
                HttpStatusCode::is4xxClientError,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .flatMap(errorResponse ->
                        Mono.error(new IncorrectRequestException(errorResponse.exceptionMessage())))
            )
            .bodyToMono(ListLinksResponse.class);
    }

    public Mono<LinkResponse> addLink(Long tgChatId, AddLinkRequest addLinkRequest) {
        return this.webClient.post()
            .uri(url)
            .headers(headers -> headers.set(header, String.valueOf(tgChatId)))
            .body(BodyInserters.fromValue(addLinkRequest))
            .retrieve()
            .onStatus(
                HttpStatusCode::is4xxClientError,
                clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                    .flatMap(errorResponse ->
                        Mono.error(new IncorrectRequestException(errorResponse.exceptionMessage())))
            )
            .bodyToMono(LinkResponse.class);
    }
}
