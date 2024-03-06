package edu.java.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.configuration.ApplicationConfig;
import edu.java.dto.GitHubRepositoryResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class GitHubRepositoriesClientTest {
    private WireMockServer wireMockServer;
    private String[] args;
    private GitHubRepositoriesClient gitHubRepositoriesClient;

    @BeforeEach
    public void setUp() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        WireMock.configureFor(wireMockServer.port());
        args = new String[2];
        args[0] = "octocat";
        args[1] = "Hello-World";
        ApplicationConfig mockConfig = Mockito.mock(ApplicationConfig.class);
        when(mockConfig.gitHubBaseUrl()).thenReturn("http://localhost:8080");
        gitHubRepositoriesClient = new GitHubRepositoriesClient(mockConfig);
    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }

    @Test
    @DisplayName("Сбор данных в соответствующий DTO")
    public void fetchDataIntoDTO() {
        // given
        stubFor(get(urlPathEqualTo("/repos/" + args[0] + "/" + args[1]))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(
                    "{\"full_name\":\"octocat/Hello-World\",\"id\":\"1296269\",\"updated_at\":\"2024-02-18T12:43:36Z\"}")));

        // when
        Mono<GitHubRepositoryResponse> answer = gitHubRepositoriesClient.fetch(args);

        // then
        assertThat(Objects.requireNonNull(answer.block()).id()).isEqualTo(1296269);
        assertThat(Objects.requireNonNull(answer.block()).fullName()).isEqualTo("octocat/Hello-World");
        assertThat(Objects.requireNonNull(answer.block()).updatedAt()).isEqualTo("2024-02-18T12:43:36Z");
    }

    @Test
    @DisplayName("Ответ 404 от сервера")
    public void repositoryNotFound() {
        // given
        stubFor(get(urlPathEqualTo("/repos/" + args[0] + "/" + args[1]))
            .willReturn(aResponse()
                .withStatus(HttpStatus.NOT_FOUND.value())));

        // when
        Mono<GitHubRepositoryResponse> answer = gitHubRepositoriesClient.fetch(args);

        // then
        WebClientResponseException exception = assertThrows(
            WebClientResponseException.class,
                answer::block
        );
        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
