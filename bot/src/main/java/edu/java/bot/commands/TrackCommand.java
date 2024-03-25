package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import dto.AddLinkRequest;
import dto.LinkResponse;
import edu.java.bot.client.LinksClient;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand implements Command {
    @Autowired
    private LinksClient client;
    private static final String COMMAND_NAME = "/track";
    private static final String COMMAND_DESCRIPTION = "Track a URL";

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
        return new TrackCommand();
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
            AddLinkRequest addLinkRequest = new AddLinkRequest();
            addLinkRequest.setLink(URI.create(stringUrl));
            LinkResponse response = this.client.addLink(chatId, addLinkRequest).block();
            return Objects.requireNonNull(response).getUrl() + " has been added to your tracked URLs list.";
        } else {
            return "Invalid URL format. Please provide a valid URL.";
        }
    }
}
