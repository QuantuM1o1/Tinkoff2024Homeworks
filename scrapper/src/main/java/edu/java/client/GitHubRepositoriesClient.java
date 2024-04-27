package edu.java.client;

import edu.java.configuration.ApplicationConfig;
import edu.java.dto.GitHubRepositoryRequest;
import edu.java.dto.GitHubRepositoryResponse;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Component
public class GitHubRepositoriesClient implements AsyncClient<GitHubRepositoryResponse, GitHubRepositoryRequest> {
    private final WebClient webClient;

    private final Retry retry;

    @Autowired
    public GitHubRepositoriesClient(ApplicationConfig applicationConfig, Retry retry) {
        this.webClient = WebClient
            .builder()
            .baseUrl(applicationConfig.gitHubBaseUrl())
            .build();
        this.retry = retry;
    }

    @Override
    public Mono<GitHubRepositoryResponse> fetch(GitHubRepositoryRequest request) {
        return this.executeWithRetry(() ->
            this.webClient
            .get()
            .uri("/repos/{owner}/{repo}", request.owner(), request.repoName())
            .retrieve()
            .bodyToMono(GitHubRepositoryResponse.class));
    }

    private <T> Mono<T> executeWithRetry(Supplier<Mono<T>> supplier) {
        return Mono.defer(supplier).retryWhen(this.retry);
    }
}
