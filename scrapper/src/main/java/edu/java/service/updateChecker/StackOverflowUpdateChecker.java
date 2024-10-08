package edu.java.service.updateChecker;

import dto.LinkUpdateRequest;
import edu.java.client.StackOverflowQuestionClient;
import edu.java.configuration.ResourcesConfig;
import edu.java.dto.LinkDTO;
import edu.java.dto.StackOverflowQuestionRequest;
import edu.java.repository.LinksRepository;
import edu.java.repository.UsersLinksRepository;
import edu.java.service.BotUpdateSender;
import edu.java.service.UpdateChecker;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
public class StackOverflowUpdateChecker implements UpdateChecker {
    private final LinksRepository linksRepository;
    private final UsersLinksRepository usersLinksRepository;
    private final Pattern pattern;
    @Autowired private StackOverflowQuestionClient stackOverflowQuestionClient;
    @Autowired private BotUpdateSender updateSender;

    public StackOverflowUpdateChecker(
        ResourcesConfig resourcesConfig, LinksRepository linksRepository, UsersLinksRepository usersLinksRepository
    ) {
        String patternString = resourcesConfig.supportedResources().get("stackoverflow.com").urlPattern();
        this.pattern = Pattern.compile(patternString);
        this.linksRepository = linksRepository;
        this.usersLinksRepository = usersLinksRepository;
    }

    @Override
    @Transactional
    public void checkForUpdates(LinkDTO link) {
        this.stackOverflowQuestionClient
            .fetch(this.createResourceRequest(link.url()))
            .subscribe(response -> {
                String description;
                if (link.commentCount() < response.items().getFirst().commentCount()) {
                    description = "New comment in StackOverflow";
                } else if (link.answerCount() < response.items().getFirst().answerCount()) {
                    description = "New answer in StackOverflow";
                } else {
                    description = "New activity in StackOverflow";
                }
                this.linksRepository.setLastActivity(link.url(), response.items().getFirst().lastActivityDate());
                this.linksRepository.setCommentCount(link.url(), response.items().getFirst().commentCount());
                this.linksRepository.setAnswerCount(link.url(), response.items().getFirst().answerCount());
                this.updateSender.sendUpdate(new LinkUpdateRequest(
                    link.id(),
                    URI.create(link.url()),
                    description,
                    this.usersLinksRepository.findAllUsersByLink(link.id())
                ));
            });
    }

    @Override
    @Transactional
    public void setInfoFirstTime(LinkDTO link) {
        this.stackOverflowQuestionClient
            .fetch(this.createResourceRequest(link.url()))
            .subscribe(response -> {
                this.linksRepository.setLastActivity(link.url(), response.items().getFirst().lastActivityDate());
                this.linksRepository.setCommentCount(link.url(), response.items().getFirst().commentCount());
                this.linksRepository.setAnswerCount(link.url(), response.items().getFirst().answerCount());
            });
    }

    private StackOverflowQuestionRequest createResourceRequest(String url) {
        Matcher matcher = this.pattern.matcher(url);
        long questionId = 0;
        if (matcher.find()) {
            questionId = Long.parseLong(matcher.group(1));
        }

        return new StackOverflowQuestionRequest("stackoverflow", questionId);
    }
}
