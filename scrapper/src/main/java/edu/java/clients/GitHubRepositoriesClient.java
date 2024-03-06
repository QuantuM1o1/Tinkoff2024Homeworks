package edu.java.clients;

import edu.java.configuration.ApplicationConfig;
import edu.java.dto.GitHubRepositoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class GitHubRepositoriesClient implements AsyncClient<GitHubRepositoryResponse, String[]> {
    private final WebClient webClient;

    @Autowired
    public GitHubRepositoriesClient(ApplicationConfig applicationConfig) {
        this.webClient = WebClient.builder()
            .baseUrl(applicationConfig.gitHubBaseUrl())
            .build();
    }

    /**
     * Fetches info, and creates a GitHubRepositoryResponse based on it
     *
     * @param args The string arguments:
     *             Argument 1: Owner.
     *             Argument 2: Repository name.
     */
    @Override
    public Mono<GitHubRepositoryResponse> fetch(String[] args) {
        return this.webClient.get()
            .uri("/repos/{owner}/{repo}", args[0], args[1])
            .retrieve().bodyToMono(GitHubRepositoryResponse.class);
    }
}
