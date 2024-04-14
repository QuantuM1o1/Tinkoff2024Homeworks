package edu.java.bot.userMessages;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.LinksClient;
import edu.java.bot.client.TgChatClient;
import edu.java.bot.commands.HelpCommand;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.commands.UntrackCommand;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.dao.UsersMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {
    HelpCommand.class,
    ListCommand.class,
    StartCommand.class,
    TrackCommand.class,
    UntrackCommand.class,
    UsersMap.class,
    UserMessageProcessorService.class,
    LinksClient.class,
    TgChatClient.class
})
public class UserMessageProcessorServiceTest {
    @Autowired
    private UserMessageProcessorService userMessageProcessor;

    @MockBean
    private ApplicationConfig applicationConfig;

    @Test
    @DisplayName("Обработка команды /help")
    void helpCommand() {
        // given
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        String testText = "/help";
        when(mockUpdate.message().text()).thenReturn(testText);
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);

        // when
        SendMessage sendMessage = this.userMessageProcessor.process(mockUpdate);
        String answer = sendMessage.getParameters().get("text").toString();

        // then
        assertThat(answer).contains("/list").contains("/start").contains("/track");
    }

    @Test
    @DisplayName("Обработка команды /track")
    void trackCommand() {
        // given
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        String testText = "/track";
        when(mockUpdate.message().text()).thenReturn(testText);
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);

        // when
        SendMessage sendMessage = this.userMessageProcessor.process(mockUpdate);
        String answer = sendMessage.getParameters().get("text").toString();

        // then
        assertThat(answer).isEqualTo("Invalid command format. Please use '/track \"url\"'.");
    }

    @Test
    @DisplayName("Обработка команды /untrack")
    void untrackCommand() {
        // given
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        String testText = "/untrack";
        when(mockUpdate.message().text()).thenReturn(testText);
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);

        // when
        SendMessage sendMessage = this.userMessageProcessor.process(mockUpdate);
        String answer = sendMessage.getParameters().get("text").toString();

        // then
        assertThat(answer).isEqualTo("Invalid command format. Please use '/untrack \"url\"'.");
    }

    @Test
    @DisplayName("Обработка неизвестной команды")
    void unknownCommand() {
        // given
        Update mockUpdate = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        String testText = "/starter";
        when(mockUpdate.message().text()).thenReturn(testText);
        Chat mockChat = Mockito.mock(Chat.class);
        when(mockUpdate.message().chat()).thenReturn(mockChat);
        when(mockUpdate.message().chat().id()).thenReturn(123456L);

        // when
        SendMessage sendMessage = this.userMessageProcessor.process(mockUpdate);
        String answer = sendMessage.getParameters().get("text").toString();

        // then
        assertThat(answer).isEqualTo("Command is unknown");
    }
}
