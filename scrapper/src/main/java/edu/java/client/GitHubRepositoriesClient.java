package edu.java.client;

import edu.java.configuration.ApplicationConfig;
import edu.java.dto.GitHubRepositoryRequest;
import edu.java.dto.GitHubRepositoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class GitHubRepositoriesClient implements AsyncClient<GitHubRepositoryResponse, GitHubRepositoryRequest> {
    private final WebClient webClient;

    @Autowired
    public GitHubRepositoriesClient(ApplicationConfig applicationConfig) {
        this.webClient = WebClient
            .builder()
            .baseUrl(applicationConfig.gitHubBaseUrl())
            .build();
    }

    @Override
    public Mono<GitHubRepositoryResponse> fetch(GitHubRepositoryRequest request) {
        return this.webClient
            .get()
            .uri("/repos/{owner}/{repo}", request.owner(), request.repoName())
            .retrieve()
            .bodyToMono(GitHubRepositoryResponse.class);
    }
}
