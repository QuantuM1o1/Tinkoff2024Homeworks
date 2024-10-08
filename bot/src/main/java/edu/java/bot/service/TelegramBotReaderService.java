package edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.userMessages.UserMessageProcessor;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelegramBotReaderService implements BotReaderService {
    private final TelegramBot bot;
    private final UserMessageProcessor userMessageProcessor;

    @Autowired
    public TelegramBotReaderService(UserMessageProcessor userMessageProcessor, TelegramBot bot) {
        this.userMessageProcessor = userMessageProcessor;
        this.bot = bot;
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            this.userMessageProcessor.process(update);
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    @PostConstruct
    public void start() {
        this.bot.setUpdatesListener(this);
    }

    @Override
    @PreDestroy
    public void close() {
        this.bot.removeGetUpdatesListener();
    }
}
