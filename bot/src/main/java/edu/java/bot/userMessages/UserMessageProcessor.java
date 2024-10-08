package edu.java.bot.userMessages;

import com.pengrad.telegrambot.model.Update;

public interface UserMessageProcessor {
    void process(Update update);
}
