package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.dto.ChatUser;
import java.net.URI;
import java.net.URISyntaxException;
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

public class UntrackCommandTest {
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
        String answer = new UntrackCommand(usersMap).name();

        // then
        assertThat(answer).isEqualTo("/untrack");
    }

    @Test
    @DisplayName("Перестать отслеживать URL")
    void stopTrackingURL() {
        // given
        String test = "https://edu.tinkoff.ru";
        String name = "Name";
        List<URI> list = new ArrayList<>();
        try {
            list.add(new URI(test));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        ChatUser user = new ChatUser(123456L, name, list);
        usersMap.put(123456L, user);
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockUpdate.message().text()).thenReturn("/untrack " + test);
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);
        when(mockUpdate.message().chat().firstName()).thenReturn(name);
        Command untrackCommand = new UntrackCommand(usersMap);

        // when
        String answer = untrackCommand.handle(mockUpdate);

        // then
        assertThat(answer).contains(test);
        assertTrue(list.isEmpty());
    }

    @Test
    @DisplayName("Пустое сообщение")
    void callWithoutMessage() {
        // given
        String name = "Name";
        List<URI> list = new ArrayList<>();
        try {
            list.add(new URI("https://edu.tinkoff.ru"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        ChatUser user = new ChatUser(123456L, name, list);
        usersMap.put(123456L, user);
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockUpdate.message().text()).thenReturn("/untrack");
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);
        when(mockUpdate.message().chat().firstName()).thenReturn(name);
        Command untrackCommand = new UntrackCommand(usersMap);

        // when
        String answer = untrackCommand.handle(mockUpdate);

        // then
        assertThat(answer).isEqualTo("Invalid command format. Please use '/untrack \"url\"'.");
        assertFalse(list.isEmpty());
    }

    @Test
    @DisplayName("Вызов со строкой, которой нет в списке URL'ов")
    void callWithStringNotInList() {
        // given
        String test = "edu.tinkoff.ru";
        String name = "Name";
        List<URI> list = new ArrayList<>();
        try {
            list.add(new URI("https://edu.tinkoff.ru"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        ChatUser user = new ChatUser(123456L, name, list);
        usersMap.put(123456L, user);
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockUpdate.message().text()).thenReturn("/untrack " + test);
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);
        when(mockUpdate.message().chat().firstName()).thenReturn(name);
        Command untrackCommand = new UntrackCommand(usersMap);

        // when
        String answer = untrackCommand.handle(mockUpdate);

        // then
        assertThat(answer).isEqualTo(test + " is not in your tracked URLs list.");
        assertFalse(list.isEmpty());
    }
}
