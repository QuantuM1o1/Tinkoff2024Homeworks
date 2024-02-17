package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dto.ChatUser;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand implements Command {
    private final Map<Long, ChatUser> usersMap;

    public TrackCommand(Map<Long, ChatUser> usersMap) {
        this.usersMap = usersMap;
    }

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "Track a URL";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        String[] messageText = update.message().text().split(" ");
        if (messageText.length != 1) {
            String url = messageText[1];
            if (isValidUrl(url)) {
                ChatUser user = usersMap.get(chatId);
                user.trackedURLs().add(url);
                return new SendMessage(chatId, "URL '" + url + "' has been added to your tracked URLs list.");
            } else {
                return new SendMessage(chatId, "Invalid URL format. Please provide a valid URL.");
            }
        } else {
            return new SendMessage(chatId, "Invalid command format. Please use '/track \"url\"'.");
        }
    }

    private boolean isValidUrl(String url) {
        Pattern pattern = Pattern.compile("^https?://\\S+$");
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }
}
