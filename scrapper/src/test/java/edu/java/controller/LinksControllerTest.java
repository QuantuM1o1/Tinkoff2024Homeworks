package edu.java.controller;

import dto.AddLinkRequest;
import dto.LinkResponse;
import dto.ListLinksResponse;
import dto.RemoveLinkRequest;
import edu.java.apiException.LinkAlreadyExistsException;
import edu.java.dto.LinkDTO;
import edu.java.service.jdbc.JdbcLinkService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class LinksControllerTest {
    private AutoCloseable closeable;

    @InjectMocks
    private final LinksController linksController = new LinksController();

    @Mock
    JdbcLinkService mockLinkService;

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
        Long tgChatId = 1L;
        String link = "https://www.google.com/";
        RemoveLinkRequest request = new RemoveLinkRequest();
        request.setLink(URI.create(link));
        doNothing().when(mockLinkService);

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
        long tgChatId = 1L;
        Collection<LinkDTO> collection = new ArrayList<>();
        when(mockLinkService.listAll(tgChatId)).thenReturn(collection);

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
        doNothing().when(mockLinkService);

        // when
        ResponseEntity<LinkResponse> response = linksController.linksPost(tgChatId, request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isEqualTo(tgChatId);
        assertThat(response.getBody().getUrl().toString()).isEqualTo(link);
    }
}
