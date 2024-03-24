package edu.java.linkUpdater;

import dto.LinkUpdateRequest;
import edu.java.client.GitHubRepositoriesClient;
import edu.java.client.StackOverflowQuestionClient;
import edu.java.client.UpdatesClient;
import edu.java.dto.GitHubRepositoryResponse;
import edu.java.dto.LinkDTO;
import edu.java.dto.StackOverflowQuestionResponse;
import edu.java.linkParser.GitHubRepositoryLinkParser;
import edu.java.linkParser.StackOverflowQuestionLinkParser;
import edu.java.service.LinkUpdaterService;
import java.net.URI;
import java.util.List;
import java.util.Objects;
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
    LinkUpdaterService updaterService;

    @Autowired UpdatesClient client;

    @Autowired StackOverflowQuestionClient stackOverflowQuestionClient;
    @Autowired GitHubRepositoriesClient gitHubRepositoriesClient;

    private static final int UPDATED_LINKS = 2;

    @Scheduled(fixedDelayString = "#{@scheduler.interval}")
    public void update() {
        log.info("Updater works!");
        List<LinkDTO> list = this.updaterService.findNLinksToUpdate(UPDATED_LINKS);
        for (LinkDTO link : list) {
            switch (link.siteId()) {
                case 1: {
                    StackOverflowQuestionResponse response = this.stackOverflowQuestionClient
                        .fetch(StackOverflowQuestionLinkParser.createRequest(link.url()))
                        .block();
                    if (Objects.requireNonNull(response).items().getFirst().lastActivityDate()
                        != link.lastActivity()) {
                        LinkUpdateRequest request = new LinkUpdateRequest();
                        request.setUrl(URI.create(link.url()));
                        client.sendUpdate(request);
                    }
                    break;
                }
                case 2: {
                    GitHubRepositoryResponse response = this.gitHubRepositoriesClient
                        .fetch(GitHubRepositoryLinkParser.createRequest(link.url()))
                        .block();
                    if (Objects.requireNonNull(response).updatedAt() != link.lastActivity()) {
                        LinkUpdateRequest request = new LinkUpdateRequest();
                        request.setUrl(URI.create(link.url()));
                        client.sendUpdate(request);
                    }
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }
}
