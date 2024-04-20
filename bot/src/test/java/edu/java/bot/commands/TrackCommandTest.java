package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import dto.AddLinkRequest;
import dto.LinkResponse;
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

public class TrackCommandTest {
    private AutoCloseable mocks;

    @Mock
    private LinksClient mockClient;

    @InjectMocks
    private TrackCommand trackCommand;

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
        String answer = new TrackCommand().name();

        // then
        assertThat(answer).isEqualTo("/track");
    }

    @Test
    @DisplayName("Начать отслеживать валидный URL")
    void trackValidUrl() {
        // given
        String name = "Name";
        String test = "https://edu.tinkoff.ru";
        long chatId = 123456L;
        AddLinkRequest addLinkRequest = new AddLinkRequest(URI.create(test));
        LinkResponse response = new LinkResponse(1L, URI.create(test));
        Mono<LinkResponse> mockMono = Mono.just(response);
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        Chat mockChat = Mockito.mock(Chat.class);


        // when
        when(this.mockClient.addLink(chatId, addLinkRequest)).thenReturn(mockMono);
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockUpdate.message().text()).thenReturn("/track " + test);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);
        when(mockUpdate.message().chat().firstName()).thenReturn(name);

        String answer = this.trackCommand.handle(mockUpdate);

        // then
        assertThat(answer).contains(test);
    }

    @Test
    @DisplayName("Вызов без ссылки")
    void trackWithoutMessage() {
        // given
        String name = "Name";
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        Chat mockChat = Mockito.mock(Chat.class);

        // when
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockUpdate.message().text()).thenReturn("/track");
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);
        when(mockUpdate.message().chat().firstName()).thenReturn(name);

        String answer = this.trackCommand.handle(mockUpdate);

        // then
        assertThat(answer).isEqualTo("Invalid command format. Please use '/track \"url\"'.");
    }

    @Test
    @DisplayName("Вызов с невалидной ссылкой")
    void trackInvalidURL() {
        // given
        String name = "Name";
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        Chat mockChat = Mockito.mock(Chat.class);

        // when
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockUpdate.message().text()).thenReturn("/track edu.tinkoff.ru");
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);
        when(mockUpdate.message().chat().firstName()).thenReturn(name);

        String answer = this.trackCommand.handle(mockUpdate);

        // then
        assertThat(answer).isEqualTo("Invalid URL format. Please provide a valid URL.");
    }
}
