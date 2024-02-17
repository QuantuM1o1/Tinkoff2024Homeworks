package edu.java.bot.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.userMessages.UserMessageProcessor;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.List;

public class TelegramBotImpl implements Bot {
    private final TelegramBot bot;
    private final UserMessageProcessor userMessageProcessor;

    public TelegramBotImpl(ApplicationConfig applicationConfig, UserMessageProcessor userMessageProcessor) {
        bot = new TelegramBot(applicationConfig.telegramToken());
        this.userMessageProcessor = userMessageProcessor;
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        bot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            this.bot.execute(userMessageProcessor.process(update));
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    @PostConstruct
    public void start() {
        bot.setUpdatesListener(this);
    }

    @Override
    @PreDestroy
    public void close() {
        bot.removeGetUpdatesListener();
    }
}
