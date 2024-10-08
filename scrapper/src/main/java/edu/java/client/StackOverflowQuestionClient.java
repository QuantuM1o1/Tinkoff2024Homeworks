package edu.java.client;

import edu.java.configuration.StackOverflowClientConfig;
import edu.java.dto.StackOverflowQuestionRequest;
import edu.java.dto.StackOverflowQuestionResponse;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Component
public class StackOverflowQuestionClient
    implements AsyncClient<StackOverflowQuestionResponse, StackOverflowQuestionRequest> {
    private static final String FILTER = "!-n0mNLma4chtannAgOY)MkBGDj9yiIQ)RB95je_QbNupq2le4kUCYa";
    private final org.springframework.web.reactive.function.client.WebClient webClient;
    private final Retry retry;

    @Autowired
    public StackOverflowQuestionClient(
        StackOverflowClientConfig stackOverflowClientConfig,
        @Qualifier("stackoverflowRetry") Retry retry
    ) {
        this.webClient = WebClient
            .builder()
            .baseUrl(stackOverflowClientConfig.baseUrl())
            .build();
        this.retry = retry;
    }

    @Override
    public Mono<StackOverflowQuestionResponse> fetch(StackOverflowQuestionRequest request) {
        return this.executeWithRetry(() ->
            this.webClient
                .get()
                .uri("/questions/{questionId}?site={site}&filter={filter}",
                    request.questionId(), request.site(), FILTER
                )
                .retrieve()
                .onStatus(
                    HttpStatusCode::isError,
                    ClientResponse::createException
                )
                .bodyToMono(StackOverflowQuestionResponse.class));
    }

    private <T> Mono<T> executeWithRetry(Supplier<Mono<T>> supplier) {
        return Mono.defer(supplier).retryWhen(this.retry);
    }
}
