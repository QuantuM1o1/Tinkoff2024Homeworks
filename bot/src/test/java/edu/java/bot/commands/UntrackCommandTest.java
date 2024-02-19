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

public class UntrackCommandTest {
    private Map<Long, ChatUser> usersMap;

    @Test
    @DisplayName("Имя")
    void name() {
        // given
        usersMap = new HashMap<>();

        // when
        String answer = new UntrackCommand(usersMap).command();

        // then
        assertThat(answer).isEqualTo("/untrack");
    }

    @Test
    @DisplayName("Описание")
    void description() {
        // given
        usersMap = new HashMap<>();

        // when
        String answer = new UntrackCommand(usersMap).description();

        // then
        assertThat(answer).isEqualTo("Untrack a URL");
    }

    @Test
    @DisplayName("Тест ручки c валидным URL")
    void handle() {
        // given
        usersMap = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("https://edu.tinkoff.ru");
        ChatUser user = new ChatUser(123456L, "Name", list);
        usersMap.put(123456L, user);
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockUpdate.message().text()).thenReturn("/untrack https://edu.tinkoff.ru");
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);
        when(mockUpdate.message().chat().firstName()).thenReturn("Name");
        Command untrackCommand = new UntrackCommand(usersMap);

        // when
        String answer = untrackCommand.handle(mockUpdate);

        // then
        assertThat(answer).isEqualTo("https://edu.tinkoff.ru has been removed from your tracked URLs list.");
    }

    @Test
    @DisplayName("Тест ручки без URL")
    void handleNoURL() {
        // given
        usersMap = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("https://edu.tinkoff.ru");
        ChatUser user = new ChatUser(123456L, "Name", list);
        usersMap.put(123456L, user);
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockUpdate.message().text()).thenReturn("/untrack");
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);
        when(mockUpdate.message().chat().firstName()).thenReturn("Name");
        Command untrackCommand = new UntrackCommand(usersMap);

        // when
        String answer = untrackCommand.handle(mockUpdate);

        // then
        assertThat(answer).isEqualTo("Invalid command format. Please use '/untrack \"url\"'.");
    }

    @Test
    @DisplayName("Тест ручки cо строкой")
    void handleString() {
        // given
        usersMap = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("https://edu.tinkoff.ru");
        ChatUser user = new ChatUser(123456L, "Name", list);
        usersMap.put(123456L, user);
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockUpdate.message().text()).thenReturn("/untrack edu.tinkoff.ru");
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);
        when(mockUpdate.message().chat().firstName()).thenReturn("Name");
        Command untrackCommand = new UntrackCommand(usersMap);

        // when
        String answer = untrackCommand.handle(mockUpdate);

        // then
        assertThat(answer).isEqualTo("edu.tinkoff.ru is not in your tracked URLs list.");
    }
}
