package edu.java.clients;

import edu.java.configuration.ApplicationConfig;
import edu.java.dto.StackOverflowDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class StackOverflowClient implements Client<StackOverflowDTO> {
    private final WebClient webClient;

    @Autowired
    public StackOverflowClient(WebClient.Builder webClientBuilder, ApplicationConfig applicationConfig) {
        this.webClient = webClientBuilder.baseUrl(applicationConfig.stackOverflowBaseUrl()).build();
    }

    @Autowired
    public StackOverflowClient(WebClient.Builder webClientBuilder, String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    /**
     * Fetches info, and creates a StackOverflowDTO based on it
     *
     * @param args The string arguments:
     *             Argument 1: Site.
     *             Argument 2: Question id.
     */
    @Override
    public Mono<StackOverflowDTO> fetch(String[] args) {
        return this.webClient.get()
            .uri("/questions/{questionId}?site={site}", args[1], args[0])
            .retrieve()
            .bodyToMono(StackOverflowDTO.class);
    }
}
