package edu.java.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.configuration.GithubClientConfig;
import edu.java.dto.GitHubRepositoryRequest;
import edu.java.dto.GitHubRepositoryResponse;
import java.time.Duration;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class GitHubRepositoriesClientTest {
    private WireMockServer wireMockServer;

    private GitHubRepositoryRequest request;

    private GitHubRepositoriesClient gitHubRepositoriesClient;

    private int retryAttempts;

    @BeforeEach
    public void setUp() {
        this.wireMockServer = new WireMockServer();
        this.wireMockServer.start();
        WireMock.configureFor(this.wireMockServer.port());
        this.request = new GitHubRepositoryRequest("octocat", "Hello-World");
        GithubClientConfig mockConfig = Mockito.mock(GithubClientConfig.class);
        when(mockConfig.baseUrl()).thenReturn("http://localhost:8080");
        this.retryAttempts = 2;
        gitHubRepositoriesClient = new GitHubRepositoriesClient(mockConfig, Retry.fixedDelay(this.retryAttempts, Duration.ZERO));
    }

    @AfterEach
    public void tearDown() {
        this.wireMockServer.stop();
    }

    @Test
    @DisplayName("Сбор данных в соответствующий DTO")
    public void fetchDataIntoDTO() {
        // given
        stubFor(get(urlPathEqualTo("/repos/" + this.request.owner() + "/" + this.request.repoName()))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(
                    "{\"full_name\":\"octocat/Hello-World\",\"id\":\"1296269\",\"updated_at\":\"2024-02-18T12:43:36Z\"}")));

        // when
        Mono<GitHubRepositoryResponse> answer = this.gitHubRepositoriesClient.fetch(this.request);

        // then
        assertThat(Objects.requireNonNull(answer.block()).id()).isEqualTo(1296269);
        assertThat(Objects.requireNonNull(answer.block()).fullName()).isEqualTo("octocat/Hello-World");
        assertThat(Objects.requireNonNull(answer.block()).updatedAt()).isEqualTo("2024-02-18T12:43:36Z");
    }

    @Test
    @DisplayName("Проверка ретраев")
    public void retryCheck() {
        // given
        stubFor(get(urlPathEqualTo("/repos/" + this.request.owner() + "/" + this.request.repoName()))
            .willReturn(aResponse().withStatus(HttpStatus.NOT_FOUND.value())));

        // when
        Mono<GitHubRepositoryResponse> answer = this.gitHubRepositoriesClient.fetch(this.request);

        // then
        Exception exception = assertThrows(RuntimeException.class, answer::block);
        assertThat(exception.getMessage()).contains("Retries exhausted");
        assertThat(exception.getMessage()).contains(String.valueOf(this.retryAttempts));
    }
}
