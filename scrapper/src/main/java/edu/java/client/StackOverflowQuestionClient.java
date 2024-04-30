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
    private static final String FILTER = "!-n0mNLma4chtannAgOY)MkBGDj9yiIQ)RB95je_QbNupq2le4kUCYa";

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
            .uri("/questions/{questionId}?site={site}&filter={filter}",
                request.questionId(), request.site(), FILTER)
            .retrieve()
            .bodyToMono(StackOverflowQuestionResponse.class);
    }
}
