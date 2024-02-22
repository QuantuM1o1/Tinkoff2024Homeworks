package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.dto.ChatUser;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

public class StartCommandTest {
    private Map<Long, ChatUser> usersMap;

    @Test
    @DisplayName("Имя")
    void name() {
        // given
        usersMap = new HashMap<>();

        // when
        String answer = new StartCommand(usersMap).name();

        // then
        assertThat(answer).isEqualTo("/start");
    }

    @Test
    @DisplayName("Описание")
    void description() {
        // given
        usersMap = new HashMap<>();

        // when
        String answer = new StartCommand(usersMap).description();

        // then
        assertThat(answer).isEqualTo("Start command");
    }

    @Test
    @DisplayName("Тест ручки")
    void handle() {
        // given
        usersMap = new HashMap<>();
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);
        when(mockUpdate.message().chat().firstName()).thenReturn("Name");
        Command startCommand = new StartCommand(usersMap);

        // when
        String answer = startCommand.handle(mockUpdate);

        // then
        assertThat(answer).isEqualTo("Hello, Name! Welcome to the notification Telegram bot.");
    }

    @Test
    @DisplayName("Тест ручки второй раз")
    void handleSecondCall() {
        // given
        usersMap = new HashMap<>();
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);
        when(mockUpdate.message().chat().firstName()).thenReturn("Name");
        Command startCommand = new StartCommand(usersMap);

        // when
        String answer = startCommand.handle(mockUpdate);
        answer = startCommand.handle(mockUpdate);

        // then
        assertThat(answer).isEqualTo("Hello again, Name! You have already started the bot.");
    }
}
