package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import dto.RemoveLinkRequest;
import edu.java.bot.client.LinksClient;
import edu.java.bot.service.TelegramBotWriterService;
import java.net.URI;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UntrackCommand implements Command {
    private static final String COMMAND_NAME = "/untrack";
    private static final String COMMAND_DESCRIPTION = "Untrack a URL";
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
            message = "Invalid command format. Please use '/untrack \"url\"'.";
            this.botWriterService.sendMessage(update.message().chat().id(), message);
            return;
        }
        String url = messageText[1];
        RemoveLinkRequest request = new RemoveLinkRequest(URI.create(url));
        this.client.deleteLink(chatId, request).subscribe(
            successfulResponse -> {
                String answer = Objects.requireNonNull(successfulResponse).url()
                    + " has been removed from your tracked URLs list.";
                this.botWriterService.sendMessage(update.message().chat().id(), answer);
            },
            error -> {
                String answer = "You're not registered";
                this.botWriterService.sendMessage(update.message().chat().id(), answer);
            }
        );
    }
}
