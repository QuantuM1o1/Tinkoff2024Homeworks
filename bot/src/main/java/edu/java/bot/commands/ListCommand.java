package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dto.ChatUser;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {
    private final Map<Long, ChatUser> usersMap;

    public ListCommand(Map<Long, ChatUser> usersMap) {
        this.usersMap = usersMap;
    }

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "List all tracked URLs";
    }

    @Override
    public String handle(Update update) {
        long chatId = update.message().chat().id();
        ChatUser user = usersMap.get(chatId);
        if (user != null && !user.trackedURLs().isEmpty()) {
            StringBuilder messageText = new StringBuilder("Tracked URLs:\n");
            for (String url : user.trackedURLs()) {
                messageText.append("- ").append(url).append("\n");
            }
            return messageText.toString();
        } else {
            return "You are not tracking any URLs.";
        }
    }
}
