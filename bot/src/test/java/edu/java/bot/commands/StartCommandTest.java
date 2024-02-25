package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.dto.ChatUser;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

public class StartCommandTest {
    private Map<Long, ChatUser> usersMap;

    @BeforeEach
    void setUp() {
        this.usersMap = new HashMap<>();
    }

    @Test
    @DisplayName("Имя")
    void name() {
        // given

        // when
        String answer = new StartCommand(usersMap).name();

        // then
        assertThat(answer).isEqualTo("/start");
    }

    @Test
    @DisplayName("Первый ответ")
    void firstReply() {
        // given
        String name = "Name";
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);
        when(mockUpdate.message().chat().firstName()).thenReturn(name);
        Command startCommand = new StartCommand(usersMap);

        // when
        String answer = startCommand.handle(mockUpdate);

        // then
        assertThat(answer).isEqualTo("Hello, " + name + "! Welcome to the notification Telegram bot.");
    }

    @Test
    @DisplayName("Второй ответ, для уже зарегирированного пользователя")
    void secondReply() {
        // given
        String name = "Name";
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);
        when(mockUpdate.message().chat().firstName()).thenReturn(name);
        Command startCommand = new StartCommand(usersMap);

        // when
        String answer = startCommand.handle(mockUpdate);
        answer = startCommand.handle(mockUpdate);

        // then
        assertThat(answer).isEqualTo("Hello again, " + name + "! You have already started the bot.");
    }
}
