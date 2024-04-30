package edu.java.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import dto.LinkUpdateRequest;
import edu.java.configuration.AccessType;
import edu.java.configuration.ApplicationConfig;
import java.net.URI;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;
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

    @BeforeEach
    public void setUp() {
        wireMockServer = new WireMockServer(8090);
        wireMockServer.start();
        WireMock.configureFor(wireMockServer.port());
        request = new LinkUpdateRequest(
            123L,
            URI.create("https://www.google.com/"),
            "google",
            new ArrayList<>()
        );
        ApplicationConfig applicationConfig = new ApplicationConfig(
            "http://localhost:8090",
            "url",
            "http://localhost:8090",
            AccessType.JDBC,
            1,
            "url",
            false);
        updatesClient = new UpdatesClient(applicationConfig);
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
        Mono<Void> answer = updatesClient.sendUpdate(request);

        // then
        assertThat(answer.block()).isNull();
    }

    @Test
    @DisplayName("Ответ 404 от сервера")
    public void userNotFound() {
        // given
        stubFor(post(urlPathEqualTo("/updates"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.NOT_FOUND.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(
                    "{\"description\":\"Didn't find some users\"," +
                        "\"code\":\"404\"," +
                        "\"exceptionName\":\"Not Found\"," +
                        "\"exceptionMessage\":\"Couldn't find users with ids [1]\"," +
                        "\"stacktrace\":null}")));

        // when
        Mono<Void> answer = updatesClient.sendUpdate(request);

        // then
        Throwable exception = assertThrows(
            Throwable.class,
            answer::block
        );
        assertThat(exception.getMessage()).contains("Couldn't find users with ids [1]");
    }
}
