package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.client.TgChatClient;
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

public class StartCommandTest {
    private AutoCloseable closeable;
    @Mock
    private TgChatClient mockClient;
    @InjectMocks
    private final StartCommand startCommand = new StartCommand();

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
        String answer = startCommand.name();

        // then
        assertThat(answer).isEqualTo("/start");
    }

    @Test
    @DisplayName("Ответ на регистрацию")
    void reply() {
        // given
        String name = "Name";
        Long chatId = 123456L;
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(chatId);
        when(mockUpdate.message().chat().firstName()).thenReturn(name);
        Mono<Void> mockMono = Mono.empty();
        when(mockClient.addChat(chatId)).thenReturn(mockMono);

        // when
        String answer = startCommand.handle(mockUpdate);

        // then
        assertThat(answer).isEqualTo("Hello, " + name + "! Welcome to the notification Telegram bot.");
    }
}
