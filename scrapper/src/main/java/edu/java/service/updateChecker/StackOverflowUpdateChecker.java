package edu.java.service.updateChecker;

import edu.java.client.StackOverflowQuestionClient;
import edu.java.configuration.ResourcesConfig;
import edu.java.dto.LinkDTO;
import edu.java.dto.StackOverflowQuestionRequest;
import edu.java.dto.StackOverflowQuestionResponse;
import edu.java.repository.LinkRepository;
import edu.java.service.UpdateChecker;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("stackoverflow.com")
public class StackOverflowUpdateChecker implements UpdateChecker {
    @Autowired
    private StackOverflowQuestionClient stackOverflowQuestionClient;

    @Autowired
    private LinkRepository linkRepository;

    private final Pattern pattern;

    @Autowired
    public StackOverflowUpdateChecker(ResourcesConfig resourcesConfig) {
        String patternString = resourcesConfig.supportedResources().get("stackoverflow.com").urlPattern();
        this.pattern = Pattern.compile(patternString);
    }

    @Override
    public Optional<String> description(String url) {
        LinkDTO link = this.linkRepository.findLinkByUrl(url).getFirst();
        StackOverflowQuestionResponse response = this.stackOverflowQuestionClient
            .fetch(this.createResourceRequest(url))
            .block();
        if (link.updatedAt() != response.items().getFirst().lastActivityDate()) {
            return Optional.of("New answer in StackOverflowQuestion");
        } else {
            return Optional.empty();
        }
    }

    @Override
    public OffsetDateTime getLastActivity(String url) {
        return Objects.requireNonNull(this.stackOverflowQuestionClient
                .fetch(this.createResourceRequest(url))
                .block())
            .items()
            .getFirst()
            .lastActivityDate();
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
