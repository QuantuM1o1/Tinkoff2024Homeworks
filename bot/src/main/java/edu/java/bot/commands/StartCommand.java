package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.client.TgChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {
    @Autowired
    private TgChatClient client;
    private static final String COMMAND_NAME = "/start";
    private static final String COMMAND_DESCRIPTION = "Start command";

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
            return "Hello, " + userName + "! Welcome to the notification Telegram bot.";
        } catch (Exception e) {
            return e.getCause().getMessage();
        }
    }

    @Override
    public Command getInstance() {
        return new StartCommand();
    }
}
