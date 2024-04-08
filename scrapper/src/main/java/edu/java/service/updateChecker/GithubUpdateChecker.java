package edu.java.service.updateChecker;

import edu.java.client.GitHubRepositoriesClient;
import edu.java.configuration.ResourcesConfig;
import edu.java.dto.GitHubRepositoryRequest;
import edu.java.dto.GitHubRepositoryResponse;
import edu.java.dto.LinkDTO;
import edu.java.repository.LinkRepository;
import edu.java.service.UpdateChecker;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("github.com")
public class GithubUpdateChecker implements UpdateChecker {
    @Autowired
    private GitHubRepositoriesClient gitHubRepositoriesClient;

    @Autowired
    private LinkRepository linkRepository;

    private final Pattern pattern;

    @Autowired
    public GithubUpdateChecker(ResourcesConfig resourcesConfig) {
        String patternString = resourcesConfig.supportedResources().get("github.com").urlPattern();
        this.pattern = Pattern.compile(patternString);
    }

    @Override
    public Optional<String> description(String url) {
        LinkDTO link = this.linkRepository.findLinkByUrl(url).getFirst();
        GitHubRepositoryResponse response = this.gitHubRepositoriesClient
            .fetch(this.createResourceRequest(link.url()))
            .block();
        if (Objects.requireNonNull(response).updatedAt() != link.updatedAt()) {
            this.linkRepository.setUpdatedAt(url, response.updatedAt());
            return Optional.of("New activity in GitHub repo");
        } else {
            return Optional.empty();
        }
    }

    @Override
    public OffsetDateTime getLastActivity(String url) {
        return Objects.requireNonNull(this.gitHubRepositoriesClient
                .fetch(this.createResourceRequest(url))
                .block())
            .updatedAt();
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
