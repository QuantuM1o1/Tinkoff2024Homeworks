package edu.java.clients;

import edu.java.configuration.ApplicationConfig;
import edu.java.dto.GitHubRepositoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class GitHubClient implements Client<GitHubRepositoryDTO> {
    private final WebClient webClient;

    @Autowired
    public GitHubClient(WebClient.Builder webClientBuilder, ApplicationConfig applicationConfig) {
        this.webClient = webClientBuilder.baseUrl(applicationConfig.gitHubBaseUrl()).build();
    }

    @Autowired
    public GitHubClient(WebClient.Builder webClientBuilder, String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    /**
     * Fetches info, and creates a GitHubRepositoryDTO based on it
     *
     * @param args The string arguments:
     *             Argument 1: Owner.
     *             Argument 2: Repository name.
     */
    @Override
    public Mono<GitHubRepositoryDTO> fetch(String[] args) {
        return this.webClient.get()
            .uri("/repos/{owner}/{repo}", args[0], args[1])
            .retrieve().bodyToMono(GitHubRepositoryDTO.class);
    }
}
