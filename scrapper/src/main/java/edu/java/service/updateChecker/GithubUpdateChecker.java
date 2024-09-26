package edu.java.service.updateChecker;

import edu.java.client.GitHubRepositoriesClient;
import edu.java.configuration.ResourcesConfig;
import edu.java.dto.GitHubRepositoryRequest;
import edu.java.dto.GitHubRepositoryResponse;
import edu.java.dto.LinkDTO;
import edu.java.dto.UpdateCheckerResponse;
import edu.java.service.UpdateChecker;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;

public class GithubUpdateChecker implements UpdateChecker {
    @Autowired
    private GitHubRepositoriesClient gitHubRepositoriesClient;

    private final Pattern pattern;

    public GithubUpdateChecker(ResourcesConfig resourcesConfig) {
        String patternString = resourcesConfig.supportedResources().get("github.com").urlPattern();
        this.pattern = Pattern.compile(patternString);
    }

    @Override
    public UpdateCheckerResponse updateLink(LinkDTO link) {
        GitHubRepositoryResponse response = this.gitHubRepositoriesClient
            .fetch(this.createResourceRequest(link.url()))
            .block();
        Optional<String> description = Optional.empty();
        if (link.updatedAt() != response.updatedAt()) {
            description = Optional.of("New activity in GitHub repo");
        }
        OffsetDateTime lastActivity = response.updatedAt();

        return new UpdateCheckerResponse(description, lastActivity, 0, 0);
    }

    private GitHubRepositoryRequest createResourceRequest(String url) {
        Matcher matcher = this.pattern.matcher(url);
        String owner = "";
        String repoName = "";
        if (matcher.find()) {
            owner = matcher.group(1);
            repoName = matcher.group(2);
        }

        return new GitHubRepositoryRequest(owner, repoName);
    }
}
