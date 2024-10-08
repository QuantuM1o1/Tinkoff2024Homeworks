package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.TelegramBotWriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnknownCommand {
    @Autowired private TelegramBotWriterService botWriterService;

    public void handle(Update update) {
        this.botWriterService.sendMessage(update.message().chat().id(),
            "Command isn't supported, use /help for list of available commands"
        );
    }
}
