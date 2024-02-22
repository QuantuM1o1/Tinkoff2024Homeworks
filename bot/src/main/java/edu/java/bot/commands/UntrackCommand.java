package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.dto.ChatUser;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand implements Command {
    private final Map<Long, ChatUser> usersMap;
    private static final String commandName = "/untrack";
    private static final String commandDescription = "Untrack a URL";

    public UntrackCommand(Map<Long, ChatUser> usersMap) {
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
        String[] messageText = update.message().text().split(" ");
        if (messageText.length != 1) {
            String url = messageText[1];
            ChatUser user = this.usersMap.get(chatId);
            if (user.trackedURLs().remove(url)) {
                return url + " has been removed from your tracked URLs list.";
            } else {
                return url + " is not in your tracked URLs list.";
            }
        } else {
            return "Invalid command format. Please use '/untrack \"url\"'.";
        }
    }
}
