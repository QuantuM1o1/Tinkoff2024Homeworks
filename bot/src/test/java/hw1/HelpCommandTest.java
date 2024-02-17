package hw1;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.HelpCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.commands.UnknownCommand;
import edu.java.bot.dto.ChatUser;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

public class HelpCommandTest {
    @Autowired
    private Map<Long, ChatUser> usersMap;
    private List<Command> commands;

    @Test
    @DisplayName("Имя")
    void name() {
        // given
        this.commands = new ArrayList<>();
        this.commands.add(new StartCommand(usersMap));
        this.commands.add(new TrackCommand(usersMap));


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
        this.commands = new ArrayList<>();
        this.commands.add(new StartCommand(usersMap));
        this.commands.add(new TrackCommand(usersMap));
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);
        HelpCommand helpCommand = new HelpCommand(commands);

        // when
        SendMessage response = helpCommand.handle(mockUpdate);

        // then
        assertThat(response).isNotNull();
    }
}
