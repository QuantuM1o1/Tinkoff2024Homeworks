package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.dto.ChatUser;
import java.net.URI;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {
    @Autowired
    private final Map<Long, ChatUser> usersMap;
    private static final String COMMAND_NAME = "/list";
    private static final String COMMAND_DESCRIPTION = "List all tracked URLs";

    public ListCommand(Map<Long, ChatUser> usersMap) {
        this.usersMap = usersMap;
    }

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
        ChatUser user = this.usersMap.get(chatId);
        if (user != null && !user.trackedURLs().isEmpty()) {
            return this.trackedURLs(user.trackedURLs());
        } else {
            return "You are not tracking any URLs.";
        }
    }

    @Override
    public Command getInstance() {
        return new ListCommand(usersMap);
    }

    private String trackedURLs(List<URI> trackedURLs) {
        StringBuilder messageText = new StringBuilder("Tracked URLs:\n");
        for (URI url : trackedURLs) {
            messageText.append("- ").append(url).append("\n");
        }
        return messageText.toString();
    }
}
