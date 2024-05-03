package edu.java.service;

import dto.LinkUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
@Service
public class KafkaBotUpdateSender implements BotUpdateSender {
    @Autowired
    private ScrapperQueueProducer scrapperQueueProducer;

    @Override
    public void sendUpdate(LinkUpdateRequest linkUpdateRequest) {
        this.scrapperQueueProducer.send(linkUpdateRequest);
    }
}
