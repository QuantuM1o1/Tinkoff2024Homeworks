package edu.java.bot.Commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class StartCommand implements Command{
    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "Start command";
    }

    @Override
    public SendMessage handle(Update update) {
        String messageText = "Hello! Welcome to our Telegram bot.";
        return new SendMessage(update.message().chat().id(), messageText);
    }
}
