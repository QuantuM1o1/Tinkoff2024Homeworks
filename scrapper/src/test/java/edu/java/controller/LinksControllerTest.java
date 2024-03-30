package edu.java.controller;

import dto.AddLinkRequest;
import dto.LinkResponse;
import dto.ListLinksResponse;
import dto.RemoveLinkRequest;
import edu.java.apiException.LinkAlreadyExistsException;
import edu.java.dto.LinkDTO;
import edu.java.service.LinkService;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class LinksControllerTest {
    private AutoCloseable closeable;

    @InjectMocks
    private final LinksController linksController = new LinksController();

    @Mock
    LinkService mockLinkService;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    @DisplayName("Удалить ссылку")
    public void deleteLink() {
        // given
        long tgChatId = 1L;
        String link = "https://www.google.com/";
        RemoveLinkRequest request = new RemoveLinkRequest(
            URI.create(link)
        );

        // when
        doNothing().when(mockLinkService);
        LinkResponse response = this.linksController.deleteLinks(tgChatId, request);

        // then
        assertThat(Objects.requireNonNull(response).id()).isEqualTo(tgChatId);
        assertThat(response.url().toString()).isEqualTo(link);
    }

    @Test
    @DisplayName("Получить ссылки")
    public void getLinks() {
        // given
        long tgChatId = 1L;
        Collection<LinkDTO> collection = new ArrayList<>();

        // when
        when(mockLinkService.listAll(tgChatId)).thenReturn(collection);
        ListLinksResponse response = linksController.getLinks(tgChatId);

        // then
        assertThat(Objects.requireNonNull(response).size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Добавить ссылку")
    public void addLink() throws LinkAlreadyExistsException
    {
        // given
        long tgChatId = 1L;
        String link = "https://www.google.com/";
        AddLinkRequest request = new AddLinkRequest(URI.create(link));

        // when
        doNothing().when(mockLinkService);
        LinkResponse response = linksController.postLinks(tgChatId, request);

        // then
        assertThat(Objects.requireNonNull(response).id()).isEqualTo(tgChatId);
        assertThat(response.url().toString()).isEqualTo(link);
    }
}
