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
    private AutoCloseable mocks;

    @Mock
    private LinksClient mockClient;

    @InjectMocks
    private ListCommand listCommand;

    @BeforeEach
    public void setUp() {
        this.mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        this.mocks.close();
    }

    @Test
    @DisplayName("Имя")
    void name() {
        // given

        // when
        String answer = this.listCommand.name();

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
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        Chat mockChat = Mockito.mock(Chat.class);

        // when
        when(this.mockClient.getLinks(chatId)).thenReturn(mockMono);
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);

        String answer = this.listCommand.handle(mockUpdate);

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
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        Chat mockChat = Mockito.mock(Chat.class);

        // when
        when(this.mockClient.getLinks(chatId)).thenReturn(mockMono);
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);
        String answer = this.listCommand.handle(mockUpdate);

        // then
        assertThat(answer).contains(test);
    }
}
