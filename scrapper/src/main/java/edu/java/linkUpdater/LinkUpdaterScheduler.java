package edu.java.linkUpdater;

import edu.java.configuration.ApplicationConfig;
import edu.java.dto.LinkDTO;
import edu.java.service.LinkService;
import edu.java.service.LinkUpdaterService;
import edu.java.service.UpdateChecker;
import java.util.List;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@EnableScheduling
@Component
public class LinkUpdaterScheduler {
    @Autowired private LinkUpdaterService updaterService;
    @Autowired private LinkService linkService;
    @Autowired private ApplicationConfig applicationConfig;
    @Autowired private Map<String, UpdateChecker> updateCheckerMap;

    @Scheduled(fixedDelayString = "#{@scheduler.interval}")
    public void update() {
        log.info("Updater works!");
        List<LinkDTO> list = this.updaterService.findNLinksToUpdate(this.applicationConfig.linksToUpdate());
        for (LinkDTO link : list) {
            if (this.updateCheckerMap.containsKey(link.domainName())) {
                this.updateCheckerMap.get(link.domainName()).checkForUpdates(link);
            }

            this.linkService.changeUpdatedAtToNow(link.url());
        }
    }
}
