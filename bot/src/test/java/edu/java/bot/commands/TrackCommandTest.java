package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.dto.ChatUser;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class TrackCommandTest {
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
        String answer = new TrackCommand(usersMap).name();

        // then
        assertThat(answer).isEqualTo("/track");
    }

    @Test
    @DisplayName("Начать отслеживать валидный URL")
    void trackValidUrl() {
        // given
        String name = "Name";
        String test = "https://edu.tinkoff.ru";
        List<URI> list = new ArrayList<>();
        ChatUser user = new ChatUser(123456L, name, list);
        usersMap.put(123456L, user);
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockUpdate.message().text()).thenReturn("/track " + test);
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);
        when(mockUpdate.message().chat().firstName()).thenReturn(name);
        Command trackCommand = new TrackCommand(usersMap);

        // when
        String answer = trackCommand.handle(mockUpdate);

        // then
        assertThat(answer).contains(test);
        assertFalse(list.isEmpty());
    }

    @Test
    @DisplayName("Вызов без ссылки")
    void trackWithoutMessage() {
        // given
        String name = "Name";
        List<URI> list = new ArrayList<>();
        ChatUser user = new ChatUser(123456L, name, list);
        usersMap.put(123456L, user);
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockUpdate.message().text()).thenReturn("/track");
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);
        when(mockUpdate.message().chat().firstName()).thenReturn(name);
        Command trackCommand = new TrackCommand(usersMap);

        // when
        String answer = trackCommand.handle(mockUpdate);

        // then
        assertThat(answer).isEqualTo("Invalid command format. Please use '/track \"url\"'.");
        assertTrue(list.isEmpty());
    }

    @Test
    @DisplayName("Вызов с невалидной ссылкой")
    void trackInvalidURL() {
        // given
        String name = "Name";
        List<URI> list = new ArrayList<>();
        ChatUser user = new ChatUser(123456L, name, list);
        usersMap.put(123456L, user);
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockUpdate.message().text()).thenReturn("/track edu.tinkoff.ru");
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);
        when(mockUpdate.message().chat().firstName()).thenReturn(name);
        Command trackCommand = new TrackCommand(usersMap);

        // when
        String answer = trackCommand.handle(mockUpdate);

        // then
        assertThat(answer).isEqualTo("Invalid URL format. Please provide a valid URL.");
        assertTrue(list.isEmpty());
    }
}
