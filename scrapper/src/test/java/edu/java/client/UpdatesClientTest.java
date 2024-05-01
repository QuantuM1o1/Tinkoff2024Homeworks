package edu.java.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import dto.LinkUpdateRequest;
import edu.java.configuration.AccessType;
import edu.java.configuration.ApplicationConfig;
import edu.java.configuration.RetryPolicy;
import edu.java.configuration.RetryType;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UpdatesClientTest {
    private WireMockServer wireMockServer;

    private UpdatesClient updatesClient;

    private LinkUpdateRequest request;

    private int retryAttempts;

    @BeforeEach
    public void setUp() {
        wireMockServer = new WireMockServer(8090);
        wireMockServer.start();
        WireMock.configureFor(wireMockServer.port());
        this.request = new LinkUpdateRequest(
            123L,
            URI.create("https://www.google.com/"),
            "google",
            new ArrayList<>()
        );
        this.retryAttempts = 2;
        RetryPolicy retryPolicy = new RetryPolicy(
            RetryType.CONSTANT,
            1,
            Duration.ZERO,
            new HashSet<>()
        );
        ApplicationConfig applicationConfig = new ApplicationConfig(
            "http://localhost:8090",
            AccessType.JDBC,
            1,
            "kafka",
            false,
            retryPolicy
        );
        this.updatesClient = new UpdatesClient(applicationConfig, Retry.fixedDelay(this.retryAttempts, Duration.ZERO));
    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }

    @Test
    @DisplayName("Отправка update")
    public void sendUpdates() {
        // given
        stubFor(post(urlPathEqualTo("/updates"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())));

        // when
        Mono<Void> answer = this.updatesClient.sendUpdate(request);

        // then
        assertThat(answer.block()).isNull();
    }

    @Test
    @DisplayName("Проверка ретраев")
    public void retryCheck() {
        // given
        stubFor(post(urlPathEqualTo("/updates"))
            .willReturn(aResponse().withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())));

        // when
        Mono<Void> answer = this.updatesClient.sendUpdate(this.request);

        // then
        Throwable exception = assertThrows(Throwable.class, answer::block);
        assertThat(exception.getMessage()).contains("Retries exhausted");
        assertThat(exception.getMessage()).contains(String.valueOf(this.retryAttempts));
    }
}
