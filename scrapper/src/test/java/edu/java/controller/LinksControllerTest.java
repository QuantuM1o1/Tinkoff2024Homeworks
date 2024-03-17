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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class LinksControllerTest
{
    private final LinksController linksController = new LinksController();

    @Test
    @DisplayName("Удалить ссылку")
    public void deleteLink() {
        // given
        Long tgChatId = 1L;
        String link = "https://www.google.com/";
        RemoveLinkRequest request = new RemoveLinkRequest();
        request.setLink(URI.create(link));

        // when
        ResponseEntity<LinkResponse> response = linksController.linksDelete(tgChatId, request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isEqualTo(tgChatId);
        assertThat(response.getBody().getUrl().toString()).isEqualTo(link);
    }

    @Test
    @DisplayName("Получить ссылки")
    public void getLinks() {
        // given
        Long tgChatId = 1L;

        // when
        ResponseEntity<ListLinksResponse> response = linksController.linksGet(tgChatId);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getSize()).isEqualTo(0);
    }

    @Test
    @DisplayName("Добавить ссылку")
    public void addLink() throws LinkAlreadyExistsException
    {
        // given
        Long tgChatId = 1L;
        String link = "https://www.google.com/";
        AddLinkRequest request = new AddLinkRequest();
        request.setLink(URI.create(link));

        // when
        ResponseEntity<LinkResponse> response = linksController.linksPost(tgChatId, request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isEqualTo(tgChatId);
        assertThat(response.getBody().getUrl().toString()).isEqualTo(link);
    }
}
