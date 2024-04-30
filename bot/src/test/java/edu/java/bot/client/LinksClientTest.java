package edu.java.bot.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import dto.AddLinkRequest;
import dto.LinkResponse;
import dto.ListLinksResponse;
import dto.RemoveLinkRequest;
import edu.java.bot.configuration.ApplicationConfig;
import java.net.URI;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LinksClientTest {
    private WireMockServer wireMockServer;

    private LinksClient linksClient;

    private long chatId;

    @BeforeEach
    public void setUp() {
        this.wireMockServer = new WireMockServer(8080);
        this.wireMockServer.start();
        WireMock.configureFor(wireMockServer.port());
        ApplicationConfig applicationConfig = new ApplicationConfig("http://localhost:8080", "token");
        this.linksClient = new LinksClient(applicationConfig);
        this.chatId = 123L;
    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
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
        Mono<LinkResponse> answer = this.linksClient.deleteLink(this.chatId, request);

        // then
        assertThat(Objects.requireNonNull(answer.block()).id()).isEqualTo(123);
        assertThat(Objects.requireNonNull(answer.block()).url().toString()).isEqualTo(url);
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
        Mono<ListLinksResponse> answer = this.linksClient.getLinks(this.chatId);

        // then
        assertThat(Objects.requireNonNull(answer.block()).size()).isEqualTo(1);
        assertThat(Objects.requireNonNull(answer.block()).links().getFirst().url().toString())
            .isEqualTo("https://www.google.com/");
        assertThat(Objects.requireNonNull(answer.block()).links().getFirst().id()).isEqualTo(12);
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
        Mono<LinkResponse> answer = this.linksClient.addLink(this.chatId, request);

        // then
        assertThat(Objects.requireNonNull(answer.block()).id()).isEqualTo(123);
        assertThat(Objects.requireNonNull(answer.block()).url().toString()).isEqualTo(link);
    }
}
