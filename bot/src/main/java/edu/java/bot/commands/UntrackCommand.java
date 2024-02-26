package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.dto.ChatUser;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand implements Command {
    @Autowired
    private final Map<Long, ChatUser> usersMap;
    private static final String COMMAND_NAME = "/untrack";
    private static final String COMMAND_DESCRIPTION = "Untrack a URL";

    public UntrackCommand(Map<Long, ChatUser> usersMap) {
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
        String[] messageText = update.message().text().split(" ");
        if (messageText.length != 1) {
            String url = messageText[1];
            return this.getMessage(url, chatId);
        } else {
            return "Invalid command format. Please use '/untrack \"url\"'.";
        }
    }

    @Override
    public Command getInstance() {
        return new UntrackCommand(usersMap);
    }

    private String getMessage(String stringUrl, long chatId) {
        ChatUser user = this.usersMap.get(chatId);
        if (user == null) {
            return "You need to register first";
        }
        try {
            if (user.trackedURLs().remove(new URI(stringUrl))) {
                return stringUrl + " has been removed from your tracked URLs list.";
            } else {
                return stringUrl + " is not in your tracked URLs list.";
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid URL format");
        }
    }
}
