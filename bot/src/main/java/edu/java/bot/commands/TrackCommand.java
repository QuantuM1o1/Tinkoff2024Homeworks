package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import dto.AddLinkRequest;
import edu.java.bot.client.LinksClient;
import edu.java.bot.service.TelegramBotWriterService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackCommand implements Command {
    private static final String COMMAND_NAME = "/track";
    private static final String COMMAND_DESCRIPTION = "Track a URL";
    @Autowired private LinksClient client;
    @Autowired private TelegramBotWriterService botWriterService;

    @Override
    public String name() {
        return COMMAND_NAME;
    }

    @Override
    public String description() {
        return COMMAND_DESCRIPTION;
    }

    @Override
    public void handle(Update update) {
        long chatId = update.message().chat().id();
        String[] messageText = update.message().text().split(" ");
        String message;
        if (messageText.length == 1) {
            message = "Invalid command format. Please use '/track \"url\"'.";
            this.botWriterService.sendMessage(update.message().chat().id(), message);
        } else {
            String url = messageText[1];
            if (!this.isValidUrl(url)) {
                message = "Invalid URL format. Please provide a valid URL.";
                this.botWriterService.sendMessage(update.message().chat().id(), message);
                return;
            }

            AddLinkRequest addLinkRequest = new AddLinkRequest(URI.create(url));
            this.client.addLink(chatId, addLinkRequest).subscribe(
                successfulResponse -> {
                    String answer = Objects.requireNonNull(successfulResponse).url()
                        + " has been added to your tracked URLs list.";
                    this.botWriterService.sendMessage(update.message().chat().id(), answer);
                },
                error -> {
                    String answer = "Uncaught error.";
                    this.botWriterService.sendMessage(update.message().chat().id(), answer);
                }
            );
        }
    }

    private boolean isValidUrl(String url) {
        try {
            URI validUrl = new URI(url);
            return validUrl.getHost() != null;
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
