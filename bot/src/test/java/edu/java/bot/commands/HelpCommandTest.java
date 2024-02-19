package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.HelpCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.commands.TrackCommand;
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

public class HelpCommandTest {
    private List<Command> commands;

    @Test
    @DisplayName("Имя")
    void name() {
        // given

        // when
        String answer = new HelpCommand(commands).command();

        // then
        assertThat(answer).isEqualTo("/help");
    }

    @Test
    @DisplayName("Описание")
    void description() {
        // given

        // when
        String answer = new HelpCommand(commands).description();

        // then
        assertThat(answer).isEqualTo("List all available commands");
    }

    @Test
    @DisplayName("Тест ручки")
    void handle() {
        // given
        Map<Long, ChatUser> usersMap = new HashMap<>();

        this.commands = new ArrayList<>();
        this.commands.add(new StartCommand(usersMap));
        this.commands.add(new TrackCommand(usersMap));
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);
        Command helpCommand = new HelpCommand(commands);

        // when
        String answer = helpCommand.handle(mockUpdate);

        // then
        assertThat(answer).isEqualTo("""
            Available commands:
            /start: Start command
            /track: Track a URL
            """);
    }
}
