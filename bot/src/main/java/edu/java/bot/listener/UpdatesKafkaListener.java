package edu.java.bot.listener;

import bot.Updates;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class UpdatesKafkaListener {
    @Autowired
    private KafkaTemplate<String, Updates.Update> updateKafkaTemplate;

    @KafkaListener(
        topics = "scrapper.update",
        groupId = "updates-group",
        containerFactory = "protobufKafkaListenerContainerFactory"
    )
    public void listen(Updates.Update update) {
        if (update.getDescription().isBlank()) {
            this.updateKafkaTemplate.send("scrapper.update.dlq", update);
        } else {
            log.info("update {} received!", update.getDescription());
        }
    }
}
