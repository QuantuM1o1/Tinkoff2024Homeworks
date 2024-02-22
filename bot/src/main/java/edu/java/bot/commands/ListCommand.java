package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.dto.ChatUser;
import java.util.List;
import java.util.Map;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {
    private final Map<Long, ChatUser> usersMap;
    private static final String commandName = "/list";
    private static final String commandDescription = "List all tracked URLs";

    public ListCommand(Map<Long, ChatUser> usersMap) {
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
        ChatUser user = this.usersMap.get(chatId);
        if (user != null && !user.trackedURLs().isEmpty()) {
            return this.trackedURLs(user.trackedURLs());
        } else {
            return "You are not tracking any URLs.";
        }
    }

    private String trackedURLs(List<String> trackedURLs) {
        StringBuilder messageText = new StringBuilder("Tracked URLs:\n");
        for (String url : trackedURLs) {
            messageText.append("- ").append(url).append("\n");
        }
        return messageText.toString();
    }
}
