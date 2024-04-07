package edu.java.bot.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import dto.AddLinkRequest;
import dto.LinkResponse;
import dto.ListLinksResponse;
import dto.RemoveLinkRequest;
import java.net.URI;
import java.util.Objects;
import edu.java.bot.configuration.ApplicationConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @BeforeEach
    public void setUp() {
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
        WireMock.configureFor(wireMockServer.port());
        ApplicationConfig applicationConfig = new ApplicationConfig("http://localhost:8080", "token");
        linksClient = new LinksClient(applicationConfig);
    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }

    @Test
    @DisplayName("Удалить ссылку")
    public void deleteLink() {
        // given
        Long chatId = 123L;
        String url = "https://www.google.com/";
        RemoveLinkRequest request = new RemoveLinkRequest(
            URI.create(url)
        );
        stubFor(delete(urlPathEqualTo("/links"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody("{\"url\":\"https://www.google.com/\", " +
                    "\"id\":\"123\"}")));

        // when
        Mono<LinkResponse> answer = linksClient.deleteLink(chatId, request);

        // then
        assertThat(Objects.requireNonNull(answer.block()).id()).isEqualTo(123);
        assertThat(Objects.requireNonNull(answer.block()).url().toString()).isEqualTo("https://www.google.com/");
    }

    @Test
    @DisplayName("Получить ссылки")
    public void getLinks() {
        // given
        Long chatId = 123L;
        stubFor(get(urlPathEqualTo("/links"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody("{\"links\":[{\"url\":\"https://www.google.com/\", \"id\":12}],\"size\":1}")));

        // when
        Mono<ListLinksResponse> answer = linksClient.getLinks(chatId);

        // then
        assertThat(Objects.requireNonNull(answer.block()).size()).isEqualTo(1);
        assertThat(Objects.requireNonNull(answer.block()).links().getFirst().url().toString()).isEqualTo("https://www.google.com/");
        assertThat(Objects.requireNonNull(answer.block()).links().getFirst().id()).isEqualTo(12);
    }

    @Test
    @DisplayName("Добавить ссылку")
    public void addLink() {
        // given
        Long chatId = 123L;
        String link = "https://www.google.com/";
        AddLinkRequest request = new AddLinkRequest(URI.create(link));
        stubFor(post(urlPathEqualTo("/links"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody("{\"url\":\"https://www.google.com/\", " +
                    "\"id\":\"123\"}")));

        // when
        Mono<LinkResponse> answer = linksClient.addLink(chatId, request);

        // then
        assertThat(Objects.requireNonNull(answer.block()).id()).isEqualTo(123);
        assertThat(Objects.requireNonNull(answer.block()).url().toString()).isEqualTo("https://www.google.com/");
    }
}
