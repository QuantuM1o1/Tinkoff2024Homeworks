package edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelegramBotWriterService implements BotWriterService {
    private final TelegramBot bot;

    @Autowired
    public TelegramBotWriterService(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public void sendMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        this.bot.execute(sendMessage);
    }
}
