package edu.java.service;

import dto.LinkUpdateRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import scrapper.Updates;

@Log4j2 @Service
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
