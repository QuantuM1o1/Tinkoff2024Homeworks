package edu.java.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class StackOverflowClient {
    private final WebClient webClient;

    @Autowired
    public StackOverflowClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.stackexchange.com/2.3").build();
    }

    @Autowired
    public StackOverflowClient(WebClient.Builder webClientBuilder, String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public Mono<String> fetch(String site, String questionId) {
        return webClient.get()
            .uri("/questions/{questionId}?site={site}", questionId, site)
            .retrieve()
            .bodyToMono(String.class);
    }
}
