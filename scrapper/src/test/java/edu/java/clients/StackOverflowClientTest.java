package edu.java.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.dto.StackOverflowDTO;
import org.junit.jupiter.api.AfterEach;
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
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class StackOverflowClientTest {
    @Autowired
    private WebClient.Builder builder;

    private WireMockServer wireMockServer;
    private String[] args;
    private StackOverflowClient stackOverflowClient;

    @BeforeEach
    public void setUp() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        WireMock.configureFor(wireMockServer.port());
        args = new String[2];
        args[0] = "stackoverflow";
        args[1] = "3615006";
        stackOverflowClient = new StackOverflowClient(builder, "http://localhost:8080");
    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }

    @Test
    @DisplayName("Сбор данных в соответствующий DTO")
    public void fetchDataIntoDTO() {
        // given
        stubFor(get(urlPathEqualTo("/questions/" + args[1]))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(
                    "{\"items\":[{\"last_activity_date\":1283320547,\"question_id\":3615006,\"link\":\"https://stackoverflow.com/questions/3615006/unit-tests-must-locate-in-the-same-package\"}]}")));

        // when
        Mono<StackOverflowDTO> answer = stackOverflowClient.fetch(args);

        // then
        assertThat(answer.block().items().getFirst().lastActivityDate()).isEqualTo(1283320547);
        assertThat(answer.block().items().getFirst().id()).isEqualTo(3615006);
        assertThat(answer.block().items().getFirst().link()).isEqualTo("https://stackoverflow.com/questions/3615006/unit-tests-must-locate-in-the-same-package");
    }

    @Test
    @DisplayName("Ответ 404 от сервера")
    public void questionNotFound() {
        // given
        stubFor(get(urlPathEqualTo("/questions/" + args[1]))
            .willReturn(aResponse()
                .withStatus(HttpStatus.NOT_FOUND.value())));

        // when
        Mono<StackOverflowDTO> answer = stackOverflowClient.fetch(args);

        // then
        WebClientResponseException exception = assertThrows(
            WebClientResponseException.class,
                answer::block
        );
        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
