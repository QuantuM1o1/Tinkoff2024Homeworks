package edu.java.service.updateChecker;

import edu.java.client.StackOverflowQuestionClient;
import edu.java.configuration.ResourcesConfig;
import edu.java.dto.LinkDTO;
import edu.java.dto.StackOverflowQuestionRequest;
import edu.java.dto.StackOverflowQuestionResponse;
import edu.java.dto.UpdateCheckerResponse;
import edu.java.repository.LinksRepository;
import edu.java.service.UpdateChecker;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2 public class StackOverflowUpdateChecker implements UpdateChecker {
    @Autowired
    private StackOverflowQuestionClient stackOverflowQuestionClient;

    private final Pattern pattern;

    public StackOverflowUpdateChecker(ResourcesConfig resourcesConfig) {
        String patternString = resourcesConfig.supportedResources().get("stackoverflow.com").urlPattern();
        this.pattern = Pattern.compile(patternString);
    }

    @Override
    public UpdateCheckerResponse updateLink(LinkDTO link) {
        StackOverflowQuestionResponse response = this.stackOverflowQuestionClient
            .fetch(this.createResourceRequest(link.url()))
            .block();
        Optional<String> description = Optional.empty();
        if (link.lastActivity() != response.items().getFirst().lastActivityDate()) {
            description = Optional.of("New answer in StackOverflowQuestion");
        }
        OffsetDateTime lastActivity = response.items().getFirst().lastActivityDate();
        int answerCount = response.items().getFirst().answerCount();
        int commentCount = response.items().getFirst().commentCount();

        return new UpdateCheckerResponse(description, lastActivity, answerCount, commentCount);
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
