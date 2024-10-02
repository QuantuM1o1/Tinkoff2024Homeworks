package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.client.TgChatClient;
import edu.java.bot.service.TelegramBotWriterService;
import exception.IncorrectRequestException;
import exception.UserAlreadyRegisteredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@Service
public class StartCommand implements Command {
    @Autowired
    private TgChatClient client;
    private static final String COMMAND_NAME = "/start";
    private static final String COMMAND_DESCRIPTION = "Start command";
    private static final String GREETINGS = "Hello, ";
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
        String userName = update.message().chat().firstName();
        this.client.addChat(chatId).subscribe(
            successfulResponse -> {
                String message = GREETINGS + userName + "! Welcome to the notification Telegram bot.";
                this.botWriterService.sendMessage(update.message().chat().id(), message);
            },
            error -> {
                String message = GREETINGS + userName + "! Welcome to the notification Telegram bot, again.";
                this.botWriterService.sendMessage(update.message().chat().id(), message);
            }

        );
    }
}
