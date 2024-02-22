package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;

public class UnknownCommand implements Command {
    @Override
    public String name() {
        return null;
    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public String handle(Update update) {
        return "Command is unknown";
    }
}
