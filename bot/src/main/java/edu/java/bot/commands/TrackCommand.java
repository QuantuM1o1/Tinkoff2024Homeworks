package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.dto.ChatUser;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand implements Command {
    @Autowired
    private final Map<Long, ChatUser> usersMap;
    private static final String COMMAND_NAME = "/track";
    private static final String COMMAND_DESCRIPTION = "Track a URL";

    public TrackCommand(Map<Long, ChatUser> usersMap) {
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
            return "Invalid command format. Please use '/track \"url\"'.";
        }
    }

    @Override
    public Command getInstance() {
        return new TrackCommand(usersMap);
    }

    private boolean isValidUrl(String url) {
        try {
            URI validUrl = new URI(url);
            return validUrl.getHost() != null;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    private String getMessage(String stringUrl, long chatId) {
        if (this.isValidUrl(stringUrl)) {
            ChatUser user = this.usersMap.get(chatId);
            URI url = URI.create(stringUrl);
            user.trackedURLs().add(url);
            return url + " has been added to your tracked URLs list.";
        } else {
            return "Invalid URL format. Please provide a valid URL.";
        }
    }
}
