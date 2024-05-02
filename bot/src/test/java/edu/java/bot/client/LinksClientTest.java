package edu.java.bot.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import dto.AddLinkRequest;
import dto.LinkResponse;
import dto.ListLinksResponse;
import dto.RemoveLinkRequest;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.configuration.RetryPolicy;
import edu.java.bot.configuration.RetryType;
import java.net.URI;
import java.time.Duration;
import java.util.HashSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LinksClientTest {
    private WireMockServer wireMockServer;

    private LinksClient linksClient;

    private long chatId;

    private int retryNumber;

    @BeforeEach
    public void setUp() {
        this.wireMockServer = new WireMockServer(8080);
        this.wireMockServer.start();
        WireMock.configureFor(wireMockServer.port());
        RetryPolicy retryPolicy = new RetryPolicy(RetryType.CONSTANT, 1, Duration.ZERO, new HashSet<>());
        ApplicationConfig applicationConfig = new ApplicationConfig("http://localhost:8080", "token", retryPolicy);
        this.retryNumber = 4;
        this.linksClient = new LinksClient(applicationConfig, Retry.fixedDelay(this.retryNumber, Duration.ZERO));
        this.chatId = 123L;
    }

    @AfterEach
    public void tearDown() {
        this.wireMockServer.stop();
    }

    @Test
    @DisplayName("Удалить ссылку")
    public void deleteLink() {
        // given
        String url = "https://www.google.com/";
        RemoveLinkRequest request = new RemoveLinkRequest(URI.create(url));
        stubFor(delete(urlPathEqualTo("/links"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody("{\"url\":\"https://www.google.com/\", " +
                    "\"id\":\"123\"}")));

        // when
        LinkResponse answer = this.linksClient.deleteLink(this.chatId, request).block();

        // then
        assertThat(answer.id()).isEqualTo(123L);
        assertThat(answer.url().toString()).isEqualTo(url);
    }

    @Test
    @DisplayName("Получить ссылки")
    public void getLinks() {
        // given
        stubFor(get(urlPathEqualTo("/links"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody("{\"links\":[{\"url\":\"https://www.google.com/\", \"id\":12}],\"size\":1}")));

        // when
        ListLinksResponse answer = this.linksClient.getLinks(this.chatId).block();

        // then
        assertThat(answer.size()).isEqualTo(1);
        assertThat(answer.links().getFirst().url().toString()).isEqualTo("https://www.google.com/");
        assertThat(answer.links().getFirst().id()).isEqualTo(12L);
    }

    @Test
    @DisplayName("Добавить ссылку")
    public void addLink() {
        // given
        String link = "https://www.google.com/";
        AddLinkRequest request = new AddLinkRequest(URI.create(link));
        stubFor(post(urlPathEqualTo("/links"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody("{\"url\":\"https://www.google.com/\", " +
                    "\"id\":\"123\"}")));

        // when
        LinkResponse answer = this.linksClient.addLink(this.chatId, request).block();

        // then
        assertThat(answer.id()).isEqualTo(123L);
        assertThat(answer.url().toString()).isEqualTo(link);
    }

    @Test
    @DisplayName("Проверка ретраев")
    public void retryCheck() {
        // given
        stubFor(get(urlPathEqualTo("/links"))
            .willReturn(aResponse().withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())));

        // when
        Mono<ListLinksResponse> answer = this.linksClient.getLinks(this.chatId);

        // then
        Exception exception = assertThrows(RuntimeException.class, answer::block);
        assertThat(exception.getMessage()).contains("Retries exhausted");
        assertThat(exception.getMessage()).contains(String.valueOf(this.retryNumber));
    }
}
