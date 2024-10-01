package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.client.TgChatClient;
import exception.IncorrectRequestException;
import exception.UserAlreadyRegisteredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j @Service
public class StartCommand implements Command {
    @Autowired
    private TgChatClient client;
    private static final String COMMAND_NAME = "/start";
    private static final String COMMAND_DESCRIPTION = "Start command";
    private static final String GREETINGS = "Hello, ";

    @Override
    public String name() {
        return COMMAND_NAME;
    }

    @Override
    public String description() {
        return COMMAND_DESCRIPTION;
    }

    @Override
    public String handle(Update update) {
        long chatId = update.message().chat().id();
        String userName = update.message().chat().firstName();
        try {
            this.client.addChat(chatId).block();
            return GREETINGS + userName + "! Welcome to the notification Telegram bot.";
        } catch (UserAlreadyRegisteredException e) {
            return GREETINGS + userName + "! Welcome to the notification Telegram bot again.";
        } catch (IncorrectRequestException e) {
            return e.getMessage();
        }
    }

    @Override
    public Command getInstance() {
        return new StartCommand();
    }
}
