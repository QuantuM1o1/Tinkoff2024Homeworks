package edu.java.bot.service;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;
import java.util.List;

public interface BotService extends AutoCloseable, UpdatesListener {
    <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request);

    int process(List<Update> updates);

    void sendMessage(long chatId, String message);

    void start();

    void close();
}
