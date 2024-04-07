package edu.java.service;

import dto.LinkUpdateRequest;
import edu.java.client.UpdatesClient;
import edu.java.configuration.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateSender {
    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private ScrapperQueueProducer scrapperQueueProducer;

    @Autowired
    private UpdatesClient updatesClient;

    public void send(LinkUpdateRequest linkUpdateRequest) {
        if (applicationConfig.useQueue()) {
            this.scrapperQueueProducer.send(linkUpdateRequest);
        } else {
            this.updatesClient.sendUpdate(linkUpdateRequest);
        }
    }
}
