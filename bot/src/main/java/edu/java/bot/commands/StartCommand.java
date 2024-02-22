package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.dto.ChatUser;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {
    private final Map<Long, ChatUser> usersMap;
    private static final String commandName = "/start";
    private static final String commandDescription = "Start command";

    public StartCommand(Map<Long, ChatUser> usersMap) {
        this.usersMap = usersMap;
    }

    @Override
    public String name() {
        return commandName;
    }

    @Override
    public String description() {
        return commandDescription;
    }

    @Override
    public String handle(Update update) {
        long chatId = update.message().chat().id();
        String userName = update.message().chat().firstName();
        if (this.usersMap.putIfAbsent(chatId, new ChatUser(chatId, userName, new ArrayList<>())) == null) {
            return "Hello, " + userName + "! Welcome to the notification Telegram bot.";
        } else {
            return "Hello again, " + userName + "! You have already started the bot.";
        }
    }
}
