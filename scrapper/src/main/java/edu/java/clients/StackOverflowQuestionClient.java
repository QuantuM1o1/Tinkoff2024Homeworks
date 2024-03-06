package edu.java.clients;

import edu.java.configuration.ApplicationConfig;
import edu.java.dto.StackOverflowQuestionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class StackOverflowQuestionClient implements AsyncClient<StackOverflowQuestionResponse, String[]> {
    private final org.springframework.web.reactive.function.client.WebClient webClient;

    @Autowired
    public StackOverflowQuestionClient(ApplicationConfig applicationConfig) {
        this.webClient = WebClient.builder()
            .baseUrl(applicationConfig.stackOverflowBaseUrl())
            .build();
    }

    /**
     * Fetches info, and creates a StackOverflowQuestionResponse based on it
     *
     * @param args The string arguments:
     *             Argument 1: Site.
     *             Argument 2: Question id.
     */
    @Override
    public Mono<StackOverflowQuestionResponse> fetch(String[] args) {
        return this.webClient.get()
            .uri("/questions/{questionId}?site={site}", args[1], args[0])
            .retrieve()
            .bodyToMono(StackOverflowQuestionResponse.class);
    }
}
