package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import dto.LinkResponse;
import dto.RemoveLinkRequest;
import edu.java.bot.client.LinksClient;
import java.net.URI;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand implements Command {
    @Autowired
    private LinksClient client;
    private static final String COMMAND_NAME = "/untrack";
    private static final String COMMAND_DESCRIPTION = "Untrack a URL";

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
        return new UntrackCommand();
    }

    private String getMessage(String stringUrl, long chatId) {
        RemoveLinkRequest request = new RemoveLinkRequest();
        request.setLink(URI.create(stringUrl));
        LinkResponse response = this.client.deleteLink(chatId, request).block();
        return Objects.requireNonNull(response).getUrl() + " has been removed from your tracked URLs list.";
    }
}
