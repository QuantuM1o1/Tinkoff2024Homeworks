package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import dto.LinkResponse;
import dto.RemoveLinkRequest;
import edu.java.bot.client.LinksClient;
import java.net.URI;
import java.util.Objects;
import edu.java.bot.service.TelegramBotWriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UntrackCommand implements Command {
    @Autowired
    private LinksClient client;
    private static final String COMMAND_NAME = "/untrack";
    private static final String COMMAND_DESCRIPTION = "Untrack a URL";
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
        if (messageText.length != 1) {
            String url = messageText[1];
            message = this.getMessage(url, chatId);
        } else {
            message = "Invalid command format. Please use '/untrack \"url\"'.";
        }

        this.botWriterService.sendMessage(update.message().chat().id(), message);
    }

    private String getMessage(String stringUrl, long chatId) {
        RemoveLinkRequest request = new RemoveLinkRequest(URI.create(stringUrl));
        LinkResponse response = this.client.deleteLink(chatId, request).block();
        return Objects.requireNonNull(response).url() + " has been removed from your tracked URLs list.";
    }
}
