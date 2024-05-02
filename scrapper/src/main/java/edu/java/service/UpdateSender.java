package edu.java.service;

import dto.LinkUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateSender {
    @Autowired
    private BotUpdateSender botUpdateSender;

    public void send(LinkUpdateRequest linkUpdateRequest) {
        this.botUpdateSender.sendUpdate(linkUpdateRequest);
    }
}
