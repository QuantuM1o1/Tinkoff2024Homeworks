package edu.java.bot.listener;

import bot.Updates;
import java.util.concurrent.CountDownLatch;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@EnableKafka
public class UpdatesKafkaListener {
    @Autowired
    private KafkaTemplate<String, Updates.Update> updateKafkaTemplate;

    private CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(
        topics = "scrapper.update",
        groupId = "updates-group",
        containerFactory = "protobufKafkaListenerContainerFactory"
    )
    public void listen(Updates.Update update) {
        log.info("Update {} received!", update.getDescription());
        this.latch.countDown();
    }

    public CountDownLatch getLatch() {
        return this.latch;
    }
}
