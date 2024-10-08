package edu.java.bot.service;

import com.pengrad.telegrambot.UpdatesListener;

public interface BotReaderService extends AutoCloseable, UpdatesListener {
    void start();
}
