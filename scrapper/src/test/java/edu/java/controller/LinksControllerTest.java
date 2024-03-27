package edu.java.controller;

import dto.AddLinkRequest;
import dto.LinkResponse;
import dto.ListLinksResponse;
import dto.RemoveLinkRequest;
import edu.java.apiException.LinkAlreadyExistsException;
import java.net.URI;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LinksControllerTest {
    private final LinksController linksController = new LinksController();

    @Test
    @DisplayName("Удалить ссылку")
    public void deleteLink() {
        // given
        Long tgChatId = 1L;
        String link = "https://www.google.com/";
        RemoveLinkRequest request = new RemoveLinkRequest(
            URI.create(link)
        );

        // when
        LinkResponse response = linksController.deleteLinks(tgChatId, request);

        // then
        assertThat(Objects.requireNonNull(response).id()).isEqualTo(tgChatId);
        assertThat(response.url().toString()).isEqualTo(link);
    }

    @Test
    @DisplayName("Получить ссылки")
    public void getLinks() {
        // given
        Long tgChatId = 1L;

        // when
        ListLinksResponse response = linksController.getLinks(tgChatId);

        // then
        assertThat(Objects.requireNonNull(response).size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Добавить ссылку")
    public void addLink() throws LinkAlreadyExistsException
    {
        // given
        Long tgChatId = 1L;
        String link = "https://www.google.com/";
        AddLinkRequest request = new AddLinkRequest(URI.create(link));

        // when
        LinkResponse response = linksController.postLinks(tgChatId, request);

        // then
        assertThat(Objects.requireNonNull(response).id()).isEqualTo(tgChatId);
        assertThat(response.url().toString()).isEqualTo(link);
    }
}
