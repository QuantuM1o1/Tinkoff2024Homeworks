package hw1;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.ListCommand;
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

public class ListCommandTest {
    private Map<Long, ChatUser> usersMap;

    @Test
    @DisplayName("Имя")
    void name() {
        // given
        usersMap = new HashMap<>();

        // when
        String answer = new ListCommand(usersMap).command();

        // then
        assertThat(answer).isEqualTo("/list");
    }

    @Test
    @DisplayName("Описание")
    void description() {
        // given
        usersMap = new HashMap<>();

        // when
        String answer = new ListCommand(usersMap).description();

        // then
        assertThat(answer).isEqualTo("List all tracked URLs");
    }

    @Test
    @DisplayName("Тест ручки")
    void handle() {
        // given
        usersMap = new HashMap<>();
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
    @DisplayName("Тест ручки с записанными URL'ами")
    void handleNotEmpty() {
        // given
        usersMap = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("https://edu.tinkoff.ru");
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
        assertThat(answer).isEqualTo("""
            Tracked URLs:
            - https://edu.tinkoff.ru
            """);
    }
}
