package edu.java.linkUpdater;

import dto.LinkUpdateRequest;
import edu.java.client.UpdatesClient;
import edu.java.configuration.ApplicationConfig;
import edu.java.dto.LinkDTO;
import edu.java.service.LinkService;
import edu.java.service.LinkUpdaterService;
import edu.java.service.UpdateChecker;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@EnableScheduling
@Component
public class LinkUpdaterScheduler {
    @Autowired
    private LinkUpdaterService updaterService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private UpdatesClient client;

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private Map<String, UpdateChecker> updateCheckerMap;

    @Scheduled(fixedDelayString = "#{@scheduler.interval}")
    public void update() {
        log.info("Updater works!");
        List<LinkDTO> list = this.updaterService.findNLinksToUpdate(this.applicationConfig.linksToUpdate());
        for (LinkDTO link : list) {
            if (this.updateCheckerMap.containsKey(link.domainName())) {
                Optional<String> optionalDescription = this.updateCheckerMap
                    .get(link.domainName())
                    .description(link.url());
                optionalDescription.ifPresent(description -> this.client.sendUpdate(new LinkUpdateRequest(
                    link.linkId(),
                    URI.create(link.url()),
                    description,
                    (List<Long>) this.linkService.findAllUsersForLink(link.linkId())
                )));
            }
        }
    }
}
