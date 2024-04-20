package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import dto.LinkResponse;
import dto.RemoveLinkRequest;
import edu.java.bot.client.LinksClient;
import java.net.URI;
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

public class UntrackCommandTest {
    private AutoCloseable mocks;

    @Mock
    private LinksClient mockClient;

    @InjectMocks
    private UntrackCommand untrackCommand;

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
        String answer = this.untrackCommand.name();

        // then
        assertThat(answer).isEqualTo("/untrack");
    }

    @Test
    @DisplayName("Перестать отслеживать URL")
    void stopTrackingURL() {
        // given
        String test = "https://edu.tinkoff.ru";
        String name = "Name";
        long chatId = 123456L;
        RemoveLinkRequest request = new RemoveLinkRequest(URI.create(test));
        LinkResponse response = new LinkResponse(1L, URI.create(test));
        Mono<LinkResponse> mockMono = Mono.just(response);
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        Chat mockChat = Mockito.mock(Chat.class);

        // when
        when(this.mockClient.deleteLink(chatId, request)).thenReturn(mockMono);
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockUpdate.message().text()).thenReturn("/untrack " + test);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);
        when(mockUpdate.message().chat().firstName()).thenReturn(name);

        String answer = this.untrackCommand.handle(mockUpdate);

        // then
        assertThat(answer).contains(test);
    }

    @Test
    @DisplayName("Пустое сообщение")
    void callWithoutMessage() {
        // given
        String test = "https://edu.tinkoff.ru";
        String name = "Name";
        long chatId = 123456L;
        RemoveLinkRequest request = new RemoveLinkRequest(URI.create(test));
        LinkResponse response = new LinkResponse(1L, URI.create(test));
        Mono<LinkResponse> mockMono = Mono.just(response);
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        Chat mockChat = Mockito.mock(Chat.class);

        // when
        when(this.mockClient.deleteLink(chatId, request)).thenReturn(mockMono);
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockUpdate.message().text()).thenReturn("/untrack");
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);
        when(mockUpdate.message().chat().firstName()).thenReturn(name);

        String answer = this.untrackCommand.handle(mockUpdate);

        // then
        assertThat(answer).isEqualTo("Invalid command format. Please use '/untrack \"url\"'.");
    }
}
