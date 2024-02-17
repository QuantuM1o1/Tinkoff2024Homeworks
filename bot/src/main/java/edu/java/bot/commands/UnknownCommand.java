package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class UnknownCommand implements Command {
    @Override
    public String command() {
        return null;
    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), "Command is unknown");
    }
}
