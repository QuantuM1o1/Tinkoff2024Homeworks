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
public class StackOverflowClientTest {
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
        String site = "stackoverflow";
        String questionId = "3615006";
        StackOverflowClient stackOverflowClient = new StackOverflowClient(builder, "http://localhost:8080");
        stubFor(get(urlPathEqualTo("/questions/" + questionId))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(
                    "{\"items\":[{\"last_activity_date\":1283320547,\"question_id\":3615006,\"link\":\"https://stackoverflow.com/questions/3615006/unit-tests-must-locate-in-the-same-package\"}]}")));

        // when
        Mono<String> answer = stackOverflowClient.fetch(site, questionId);

        // then
        Assertions.assertEquals(
            "{\"items\":[{\"last_activity_date\":1283320547,\"question_id\":3615006,\"link\":\"https://stackoverflow.com/questions/3615006/unit-tests-must-locate-in-the-same-package\"}]}",
            answer.block()
        );
    }

    @Test
    @DisplayName("Нет вопроса")
    public void notFound() {
        // given
        String site = "stackoverflow";
        String questionId = "3615006";
        StackOverflowClient stackOverflowClient = new StackOverflowClient(builder, "http://localhost:8080");
        stubFor(get(urlPathEqualTo("/questions/" + questionId))
            .willReturn(aResponse()
                .withStatus(HttpStatus.NOT_FOUND.value())));

        // when
        Mono<String> answer = stackOverflowClient.fetch(site, questionId);

        // then
        WebClientResponseException exception = assertThrows(
            WebClientResponseException.class,
            () -> stackOverflowClient.fetch(site, questionId).block()
        );
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}
