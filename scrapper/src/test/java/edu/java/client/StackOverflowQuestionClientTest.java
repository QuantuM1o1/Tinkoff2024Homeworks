package edu.java.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.configuration.StackOverflowClientConfig;
import edu.java.dto.StackOverflowQuestionRequest;
import edu.java.dto.StackOverflowQuestionResponse;
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

public class StackOverflowQuestionClientTest {
    private WireMockServer wireMockServer;

    private StackOverflowQuestionRequest request;

    private StackOverflowQuestionClient stackOverflowQuestionClient;

    private int retryAttempts;

    @BeforeEach
    public void setUp() {
        this.wireMockServer = new WireMockServer();
        this.wireMockServer.start();
        WireMock.configureFor(this.wireMockServer.port());
        this.request = new StackOverflowQuestionRequest("stackoverflow", 3615006L);
        StackOverflowClientConfig mockConfig = Mockito.mock(StackOverflowClientConfig.class);
        this.retryAttempts = 3;
        when(mockConfig.baseUrl()).thenReturn("http://localhost:8080");
        this.stackOverflowQuestionClient
            = new StackOverflowQuestionClient(mockConfig, Retry.fixedDelay(this.retryAttempts, Duration.ZERO));
    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }

    @Test
    @DisplayName("Сбор данных в соответствующий DTO")
    public void fetchDataIntoDTO() {
        // given
        stubFor(get(urlPathEqualTo("/questions/" + this.request.questionId()))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(
                    "{\"items\":[{" +
                        "\"comment_count\": 1," +
                        "\"answer_count\": 2," +
                        "\"last_activity_date\": 1283320547," +
                        "\"question_id\": 3615006," +
                        "\"link\": \"https://stackoverflow.com/questions/3615006/unit-tests-must-locate-in-the-same-package\"}]}")));

        // when
        Mono<StackOverflowQuestionResponse> answer = this.stackOverflowQuestionClient.fetch(this.request);

        // then
        assertThat(Objects.requireNonNull(answer.block()).items().getFirst().lastActivityDate())
            .isEqualTo("2010-09-01T05:55:47Z");
        assertThat(Objects.requireNonNull(answer.block()).items().getFirst().id())
            .isEqualTo(3615006);
        assertThat(Objects.requireNonNull(answer.block()).items().getFirst().link())
            .isEqualTo("https://stackoverflow.com/questions/3615006/unit-tests-must-locate-in-the-same-package");
    }

    @Test
    @DisplayName("Проверка ретраев")
    public void retryCheck() {
        // given
        stubFor(get(urlPathEqualTo("/questions/" + this.request.questionId()))
            .willReturn(aResponse().withStatus(HttpStatus.NOT_FOUND.value())));

        // when
        Mono<StackOverflowQuestionResponse> answer = this.stackOverflowQuestionClient.fetch(this.request);

        // then
        Exception exception = assertThrows(RuntimeException.class, answer::block);
        assertThat(exception.getMessage()).contains("Retries exhausted");
        assertThat(exception.getMessage()).contains(String.valueOf(this.retryAttempts));
    }
}
