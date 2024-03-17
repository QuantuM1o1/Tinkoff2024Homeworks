package edu.java.bot.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class TgChatClientTest {
    private WireMockServer wireMockServer;
    @Autowired
    private TgChatClient tgChatClient;

    @BeforeEach
    public void setUp() {
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
        WireMock.configureFor(wireMockServer.port());
    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }

    @Test
    @DisplayName("Удалить чат")
    public void deleteChat() {
        // given
        Long chatId = 123L;
        stubFor(delete(urlPathEqualTo("/tg-chat/123"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())));

        // when
        Mono<Void> answer = tgChatClient.deleteChat(chatId);

        // then
        assertThat(answer.block()).isNull();
    }

    @Test
    @DisplayName("Добавить чат")
    public void addChat() {
        // given
        Long chatId = 123L;
        stubFor(post(urlPathEqualTo("/tg-chat/123"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())));

        // when
        Mono<Void> answer = tgChatClient.addChat(chatId);

        // then
        assertThat(answer.block()).isNull();
    }
}
