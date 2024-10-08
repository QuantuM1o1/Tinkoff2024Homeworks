package edu.java.bot.service;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateNotifier {
    @Autowired
    BotWriterService botWriterService;

    public void notifyUser(long userId, String description, URI url) {
        this.botWriterService.sendMessage(userId, description + "\n" + url.toString());
    }
}
