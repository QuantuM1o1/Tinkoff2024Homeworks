package edu.java.client;

import edu.java.configuration.ApplicationConfig;
import edu.java.dto.StackOverflowQuestionRequest;
import edu.java.dto.StackOverflowQuestionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class StackOverflowQuestionClient
    implements AsyncClient<StackOverflowQuestionResponse, StackOverflowQuestionRequest> {
    private final org.springframework.web.reactive.function.client.WebClient webClient;

    @Autowired
    public StackOverflowQuestionClient(ApplicationConfig applicationConfig) {
        this.webClient = WebClient
            .builder()
            .baseUrl(applicationConfig.stackOverflowBaseUrl())
            .build();
    }

    @Override
    public Mono<StackOverflowQuestionResponse> fetch(StackOverflowQuestionRequest request) {
        return this.webClient
            .get()
            .uri("/questions/{questionId}?site={site}", request.questionId(), request.site())
            .retrieve()
            .bodyToMono(StackOverflowQuestionResponse.class);
    }
}
