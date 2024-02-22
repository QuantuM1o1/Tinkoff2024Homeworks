package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.dto.ChatUser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

public class TrackCommandTest {
    private Map<Long, ChatUser> usersMap;

    @Test
    @DisplayName("Имя")
    void name() {
        // given
        usersMap = new HashMap<>();

        // when
        String answer = new TrackCommand(usersMap).name();

        // then
        assertThat(answer).isEqualTo("/track");
    }

    @Test
    @DisplayName("Описание")
    void description() {
        // given
        usersMap = new HashMap<>();

        // when
        String answer = new TrackCommand(usersMap).description();

        // then
        assertThat(answer).isEqualTo("Track a URL");
    }

    @Test
    @DisplayName("Тест ручки c валидным URL")
    void handle() {
        // given
        usersMap = new HashMap<>();
        List<String> list = new ArrayList<>();
        ChatUser user = new ChatUser(123456L, "Name", list);
        usersMap.put(123456L, user);
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockUpdate.message().text()).thenReturn("/track https://edu.tinkoff.ru");
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);
        when(mockUpdate.message().chat().firstName()).thenReturn("Name");
        Command trackCommand = new TrackCommand(usersMap);

        // when
        String answer = trackCommand.handle(mockUpdate);

        // then
        assertThat(answer).isEqualTo("https://edu.tinkoff.ru has been added to your tracked URLs list.");
    }

    @Test
    @DisplayName("Тест ручки без URL")
    void handleNoURL() {
        // given
        usersMap = new HashMap<>();
        List<String> list = new ArrayList<>();
        ChatUser user = new ChatUser(123456L, "Name", list);
        usersMap.put(123456L, user);
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockUpdate.message().text()).thenReturn("/track");
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);
        when(mockUpdate.message().chat().firstName()).thenReturn("Name");
        Command trackCommand = new TrackCommand(usersMap);

        // when
        String answer = trackCommand.handle(mockUpdate);

        // then
        assertThat(answer).isEqualTo("Invalid command format. Please use '/track \"url\"'.");
    }

    @Test
    @DisplayName("Тест ручки cо строкой")
    void handleString() {
        // given
        usersMap = new HashMap<>();
        List<String> list = new ArrayList<>();
        ChatUser user = new ChatUser(123456L, "Name", list);
        usersMap.put(123456L, user);
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockUpdate.message().text()).thenReturn("/track edu.tinkoff.ru");
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);
        when(mockUpdate.message().chat().firstName()).thenReturn("Name");
        Command trackCommand = new TrackCommand(usersMap);

        // when
        String answer = trackCommand.handle(mockUpdate);

        // then
        assertThat(answer).isEqualTo("Invalid URL format. Please provide a valid URL.");
    }
}
