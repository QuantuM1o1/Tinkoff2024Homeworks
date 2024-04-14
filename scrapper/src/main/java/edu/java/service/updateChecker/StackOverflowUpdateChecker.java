package edu.java.service.updateChecker;

import edu.java.client.StackOverflowQuestionClient;
import edu.java.configuration.ResourcesConfig;
import edu.java.dto.LinkDTO;
import edu.java.dto.StackOverflowQuestionRequest;
import edu.java.dto.StackOverflowQuestionResponse;
import edu.java.property.SupportedResource;
import edu.java.repository.LinkRepository;
import edu.java.service.UpdateChecker;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;

public class StackOverflowUpdateChecker implements UpdateChecker {
    @Autowired
    private StackOverflowQuestionClient stackOverflowQuestionClient;

    private final LinkRepository linkRepository;

    @Autowired
    private ResourcesConfig resourcesConfig;

    public StackOverflowUpdateChecker(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public Optional<String> description(String url) {
        LinkDTO link = this.linkRepository.findLinkByUrl(url).getFirst();
        StackOverflowQuestionResponse response = this.stackOverflowQuestionClient
            .fetch(this.createResourceRequest(url, this.resourcesConfig.supportedResources().get(link.domainName())))
            .block();
        if (link.updatedAt() != response.items().getFirst().lastActivityDate()) {
            return Optional.of("New answer in StackOverflowQuestion");
        } else {
            return Optional.empty();
        }
    }

    @Override
    public OffsetDateTime getLastActivity(String url, String domain) {
        return Objects.requireNonNull(this.stackOverflowQuestionClient
                .fetch(this.createResourceRequest(url, this.resourcesConfig.supportedResources().get(domain)))
                .block())
            .items()
            .getFirst()
            .lastActivityDate();
    }

    @Override
    public int getAnswerCount(String url, String domain) {
        return Objects.requireNonNull(this.stackOverflowQuestionClient
                .fetch(this.createResourceRequest(url, this.resourcesConfig.supportedResources().get(domain)))
                .block())
            .items()
            .getFirst()
            .answerCount();
    }

    @Override
    public int getCommentCount(String url, String domain) {
        return Objects.requireNonNull(this.stackOverflowQuestionClient
                .fetch(this.createResourceRequest(url, this.resourcesConfig.supportedResources().get(domain)))
                .block())
            .items()
            .getFirst()
            .commentCount();
    }

    private StackOverflowQuestionRequest createResourceRequest(String url, SupportedResource resource) {
        String patternString = resource.urlPattern();
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(url);
        long questionId = 0;
        if (matcher.find()) {
            questionId = Long.parseLong(matcher.group(1));
        }

        return new StackOverflowQuestionRequest("stackoverflow", questionId);
    }
}
