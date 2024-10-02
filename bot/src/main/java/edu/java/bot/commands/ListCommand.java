package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import dto.LinkResponse;
import dto.ListLinksResponse;
import edu.java.bot.client.LinksClient;
import java.util.List;
import java.util.Objects;
import edu.java.bot.service.TelegramBotWriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListCommand implements Command {
    @Autowired private LinksClient client;
    private static final String COMMAND_NAME = "/list";
    private static final String COMMAND_DESCRIPTION = "List all tracked URLs";
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
        ListLinksResponse response = this.client.getLinks(chatId).block();
        String message;
        if (!Objects.requireNonNull(response).links().isEmpty()) {
            message = this.trackedURLs(response.links());
        } else {
            message = "You are not tracking any URLs.";
        }

        this.botWriterService.sendMessage(update.message().chat().id(), message);
    }

    private String trackedURLs(List<LinkResponse> trackedURLs) {
        StringBuilder messageText = new StringBuilder("Tracked URLs:\n");
        for (LinkResponse linkResponse : trackedURLs) {
            messageText.append("- ").append(linkResponse.url()).append("\n");
        }
        return messageText.toString();
    }
}
