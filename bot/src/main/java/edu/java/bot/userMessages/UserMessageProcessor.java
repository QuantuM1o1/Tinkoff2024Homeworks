package edu.java.bot.userMessages;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface UserMessageProcessor {
    void process(Update update);
}
