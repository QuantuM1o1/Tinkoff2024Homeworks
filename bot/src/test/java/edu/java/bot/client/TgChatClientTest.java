package edu.java.bot.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.configuration.RetryPolicy;
import edu.java.bot.configuration.RetryType;
import java.time.Duration;
import java.util.HashSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TgChatClientTest {
    private WireMockServer wireMockServer;
    private TgChatClient tgChatClient;
    private long chatId;

    @BeforeEach
    public void setUp() {
        this.wireMockServer = new WireMockServer(8080);
        this.wireMockServer.start();
        WireMock.configureFor(wireMockServer.port());
        RetryPolicy retryPolicy = new RetryPolicy(RetryType.CONSTANT, 1, Duration.ZERO, new HashSet<>());
        ApplicationConfig applicationConfig = new ApplicationConfig(
            "token",
            "http://localhost:8080",
            retryPolicy
        );
        int retryNumber = 5;
        this.tgChatClient = new TgChatClient(applicationConfig, Retry.fixedDelay(retryNumber, Duration.ZERO));
        this.chatId = 123L;
    }

    @AfterEach
    public void tearDown() {
        this.wireMockServer.stop();
    }

    @Test
    @DisplayName("Удалить чат")
    public void deleteChat() {
        // given
        stubFor(delete(urlPathEqualTo("/tg-chat/123"))
            .willReturn(aResponse().withStatus(HttpStatus.OK.value())));

        // when
        Mono<Void> answer = this.tgChatClient.deleteChat(chatId);

        // then
        assertThat(answer.block()).isNull();
    }

    @Test
    @DisplayName("Добавить чат")
    public void addChat() {
        // given
        stubFor(post(urlPathEqualTo("/tg-chat/123"))
            .willReturn(aResponse().withStatus(HttpStatus.OK.value())));

        // when
        Mono<Void> answer = this.tgChatClient.addChat(chatId);

        // then
        assertThat(answer.block()).isNull();
    }
}
