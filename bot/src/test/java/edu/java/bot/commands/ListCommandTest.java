package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import dto.LinkResponse;
import dto.ListLinksResponse;
import edu.java.bot.client.LinksClient;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

public class ListCommandTest {
    private AutoCloseable closeable;
    @Mock
    private LinksClient mockClient;
    @InjectMocks
    private final ListCommand listCommand = new ListCommand();

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    @DisplayName("Имя")
    void name() {
        // given

        // when
        String answer = listCommand.name();

        // then
        assertThat(answer).isEqualTo("/list");
    }

    @Test
    @DisplayName("Лист пуст")
    void CallToEmptyList() {
        // given
        long chatId = 123456L;
        ListLinksResponse list = new ListLinksResponse(
            new ArrayList<>(),
            0
        );
        Mono<ListLinksResponse> mockMono = Mono.just(list);
        when(mockClient.getLinks(chatId)).thenReturn(mockMono);
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);

        // when
        String answer = listCommand.handle(mockUpdate);

        // then
        assertThat(answer).isEqualTo("You are not tracking any URLs.");
    }

    @Test
    @DisplayName("Лист с записанным URL")
    void callToListWithOneURL() {
        // given
        String test = "https://edu.tinkoff.ru";
        List<LinkResponse> listLinks = new ArrayList<>();
        LinkResponse linkResponse = new LinkResponse(1L, URI.create(test));
        listLinks.add(linkResponse);
        long chatId = 123456L;
        ListLinksResponse list = new ListLinksResponse(
            listLinks,
            listLinks.size()
        );
        Mono<ListLinksResponse> mockMono = Mono.just(list);
        when(mockClient.getLinks(chatId)).thenReturn(mockMono);
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);

        // when
        String answer = listCommand.handle(mockUpdate);

        // then
        assertThat(answer).contains(test);
    }
}
