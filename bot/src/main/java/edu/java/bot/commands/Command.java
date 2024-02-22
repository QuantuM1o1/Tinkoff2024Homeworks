package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;

public interface Command {
    String name();

    String description();

    String handle(Update update);

    default boolean supports(Update update) {
        return update.message().text().startsWith(this.name() + " ")
            || update.message().text().equals(this.name());
    }
}
