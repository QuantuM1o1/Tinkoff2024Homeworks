package edu.java.bot.service;

import edu.java.bot.apiException.UserNotFoundException;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateNotifier {
    @Autowired
    BotService botService;

    public void notifyUser(long userId, String description, URI url) throws UserNotFoundException {
        this.botService.sendMessage(userId, description + "\n" + url.toString());
    }
}
