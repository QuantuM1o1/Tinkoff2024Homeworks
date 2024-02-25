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
import static org.mockito.Mockito.when;

public class ListCommandTest {
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
        String answer = new ListCommand(usersMap).name();

        // then
        assertThat(answer).isEqualTo("/list");
    }

    @Test
    @DisplayName("Лист пуст")
    void CallToEmptyList() {
        // given
        ChatUser user = new ChatUser(123456L, "Name", new ArrayList<>());
        usersMap.put(123456L, user);
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);
        Command listCommand = new ListCommand(usersMap);

        // when
        String answer = listCommand.handle(mockUpdate);

        // then
        assertThat(answer).isEqualTo("You are not tracking any URLs.");
    }

    @Test
    @DisplayName("Лист с записанным URL")
    void callToListWithOneURL() {
        // given
        List<URI> list = new ArrayList<>();
        String test = "https://edu.tinkoff.ru";
        try {
            list.add(new URI(test));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        ChatUser user = new ChatUser(123456L, "Name", list);
        usersMap.put(123456L, user);
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);
        Command listCommand = new ListCommand(usersMap);

        // when
        String answer = listCommand.handle(mockUpdate);

        // then
        assertThat(answer).contains(test);
    }
}
