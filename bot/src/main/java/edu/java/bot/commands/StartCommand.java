package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import dto.ApiErrorResponse;
import edu.java.bot.client.TgChatClient;
import edu.java.bot.service.TelegramBotWriterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StartCommand implements Command {
    private static final String COMMAND_NAME = "/start";
    private static final String COMMAND_DESCRIPTION = "Start command";
    private static final String GREETINGS = "Hello, ";
    @Autowired private TgChatClient client;
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
                log.error(error.toString());
                String message =
                    error instanceof ApiErrorResponse ? ((ApiErrorResponse) error).getCode().equals("409") ?
                        GREETINGS + userName + "! Welcome to the notification Telegram bot, again." :
                        GREETINGS + userName + "! Our bot has broken." : "Uncaught error.";
                this.botWriterService.sendMessage(update.message().chat().id(), message);
            }
        );
    }
}
