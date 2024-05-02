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

import edu.java.bot.dao.UsersMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserMessageProcessorServiceTest {
    @Autowired
    private UserMessageProcessorService userMessageProcessor;

    @MockBean
    private LinksClient linksClient;

    @MockBean
    private TgChatClient tgChatClient;

    private Update mockUpdate;

    private Message mockMessage;

    private Chat mockChat;

    @BeforeEach
    void setUp() {
        this.mockUpdate = Mockito.mock(Update.class);
        this.mockMessage = Mockito.mock(Message.class);
        this.mockChat = Mockito.mock(Chat.class);
    }

    @Test
    @DisplayName("Обработка команды /help")
    void helpCommand() {
        // given
        String testText = "/help";

        // when
        when(this.mockUpdate.message()).thenReturn(this.mockMessage);
        when(this.mockUpdate.message().text()).thenReturn(testText);
        when(this.mockUpdate.message().chat()).thenReturn(this.mockChat);
        when(this.mockUpdate.message().chat().id()).thenReturn(123456L);

        SendMessage sendMessage = this.userMessageProcessor.process(this.mockUpdate);
        String answer = sendMessage.getParameters().get("text").toString();

        // then
        assertThat(answer).contains("/list").contains("/start").contains("/track");
    }

    @Test
    @DisplayName("Обработка команды /track")
    void trackCommand() {
        // given
        String testText = "/track";

        // when
        when(this.mockUpdate.message()).thenReturn(this.mockMessage);
        when(mockUpdate.message().text()).thenReturn(testText);
        when(this.mockUpdate.message().chat()).thenReturn(this.mockChat);
        when(this.mockUpdate.message().chat().id()).thenReturn(123456L);

        SendMessage sendMessage = this.userMessageProcessor.process(this.mockUpdate);
        String answer = sendMessage.getParameters().get("text").toString();

        // then
        assertThat(answer).isEqualTo("Invalid command format. Please use '/track \"url\"'.");
    }

    @Test
    @DisplayName("Обработка команды /untrack")
    void untrackCommand() {
        // given
        String testText = "/untrack";

        // when
        when(this.mockUpdate.message()).thenReturn(this.mockMessage);
        when(this.mockUpdate.message().text()).thenReturn(testText);
        when(this.mockUpdate.message().chat()).thenReturn(this.mockChat);
        when(this.mockUpdate.message().chat().id()).thenReturn(123456L);

        SendMessage sendMessage = this.userMessageProcessor.process(mockUpdate);
        String answer = sendMessage.getParameters().get("text").toString();

        // then
        assertThat(answer).isEqualTo("Invalid command format. Please use '/untrack \"url\"'.");
    }

    @Test
    @DisplayName("Обработка неизвестной команды")
    void unknownCommand() {
        // given
        String testText = "/starter";

        // when
        when(this.mockUpdate.message()).thenReturn(this.mockMessage);
        when(this.mockUpdate.message().text()).thenReturn(testText);
        when(this.mockUpdate.message().chat()).thenReturn(this.mockChat);
        when(this.mockUpdate.message().chat().id()).thenReturn(123456L);

        SendMessage sendMessage = this.userMessageProcessor.process(mockUpdate);
        String answer = sendMessage.getParameters().get("text").toString();

        // then
        assertThat(answer).isEqualTo("Command is unknown");
    }
}
