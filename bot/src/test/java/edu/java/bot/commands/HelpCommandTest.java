package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

public class HelpCommandTest {
    @Autowired
    private List<Command> commands;

    @Test
    @DisplayName("Имя")
    void name() {
        // given

        // when
        String answer = new HelpCommand(this.commands).name();

        // then
        assertThat(answer).isEqualTo("/help");
    }

    @Test
    @DisplayName("Ответ на команду")
    void commandRespond() {
        // given
        this.commands = new ArrayList<>();
        Command testCommand1 = new StartCommand();
        Command testCommand2 = new TrackCommand();
        this.commands.add(testCommand1);
        this.commands.add(testCommand2);
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);
        Command helpCommand = new HelpCommand(this.commands);

        // when
        String answer = helpCommand.handle(mockUpdate);

        // then
        assertThat(answer).contains(testCommand1.name()).contains(testCommand2.name());
    }
}
