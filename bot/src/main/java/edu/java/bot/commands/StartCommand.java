package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dto.ChatUser;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {
    private final Map<Long, ChatUser> usersMap;

    public StartCommand(Map<Long, ChatUser> usersMap) {
        this.usersMap = usersMap;
    }

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "Start command";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        String userName = update.message().chat().firstName();
        SendMessage response;
        if (usersMap.containsKey(chatId)) {
            response = new SendMessage(chatId, "Hello again, " + userName + "! You have already started the bot.");
        } else {
            usersMap.put(chatId, new ChatUser(chatId, userName, new ArrayList<>()));
            response = new SendMessage(chatId, "Hello, " + userName + "! Welcome to the notification Telegram bot.");
        }
        return response;
    }
}
