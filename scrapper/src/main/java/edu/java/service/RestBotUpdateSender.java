package edu.java.service;

import dto.LinkUpdateRequest;
import edu.java.client.UpdatesClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Log4j2 @ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
@Service
public class RestBotUpdateSender implements BotUpdateSender {
    @Autowired
    private UpdatesClient updatesClient;

    @Override
    public void sendUpdate(LinkUpdateRequest linkUpdateRequest) {
        this.updatesClient.sendUpdate(linkUpdateRequest);
    }
}
