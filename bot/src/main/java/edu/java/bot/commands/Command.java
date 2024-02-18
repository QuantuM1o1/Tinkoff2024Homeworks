package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;

public interface Command {
    String command();

    String description();

    String handle(Update update);

    default boolean supports(Update update) {
        return update.message().text().startsWith(this.command() + " ")
            || update.message().text().equals(this.command());
    }
}
