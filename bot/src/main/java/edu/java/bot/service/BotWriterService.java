package edu.java.bot.service;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import java.util.List;

public interface BotWriterService {
    void sendMessage(long chatId, String message);
}
