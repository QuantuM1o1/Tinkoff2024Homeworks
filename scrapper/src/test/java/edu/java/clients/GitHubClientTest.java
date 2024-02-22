package edu.java.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class GitHubClientTest {
    @Autowired
    private WebClient.Builder builder;

    private WireMockServer wireMockServer;

    @BeforeEach
    public void beforeE() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        WireMock.configureFor(wireMockServer.port());
    }

    @AfterEach
    public void afterE() {
        wireMockServer.stop();
    }

    @Test
    @DisplayName("Воровство данных")
    public void fetch() {
        // given
        String owner = "octocat";
        String repoName = "Hello-World";
        GitHubClient gitHubClient = new GitHubClient(builder, "http://localhost:8080");
        stubFor(get(urlPathEqualTo("/repos/" + owner + "/" + repoName))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(
                    "{\"full_name\":\"octocat/Hello-World\",\"id\":\"1296269\",\"updated_at\":\"2024-02-18T12:43:36Z\"}")));

        // when
        Mono<String> answer = gitHubClient.fetch(owner, repoName);

        // then
        Assertions.assertEquals(
            "{\"full_name\":\"octocat/Hello-World\",\"id\":\"1296269\",\"updated_at\":\"2024-02-18T12:43:36Z\"}",
            answer.block());
    }

    @Test
    @DisplayName("Нет репки")
    public void notFound() {
        // given
        String owner = "octocat";
        String repoName = "Hello-World";
        GitHubClient gitHubClient = new GitHubClient(builder, "http://localhost:8080");
        stubFor(get(urlPathEqualTo("/repos/" + owner + "/" + repoName))
            .willReturn(aResponse()
                .withStatus(HttpStatus.NOT_FOUND.value())));

        // when

        // then
        WebClientResponseException exception = assertThrows(
            WebClientResponseException.class,
            () -> gitHubClient.fetch(owner, repoName).block()
        );
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}
