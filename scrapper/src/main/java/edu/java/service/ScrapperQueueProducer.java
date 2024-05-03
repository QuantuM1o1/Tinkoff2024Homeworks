package edu.java.service;

import dto.LinkUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import scrapper.Updates;

@Service
public class ScrapperQueueProducer {
    @Autowired
    private KafkaTemplate<String, Updates.Update> updateKafkaTemplate;

    public void send(LinkUpdateRequest update) {
        Updates.Update request = Updates.Update.newBuilder()
            .setId(update.id())
            .setUrl(update.url().toString())
            .setDescription(update.description())
            .addAllTgChatIds(update.tgChatIds())
            .build();
        this.updateKafkaTemplate.send("scrapper.update", request);
    }
}
