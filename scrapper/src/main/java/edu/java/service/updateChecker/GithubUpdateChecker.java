package edu.java.service.updateChecker;

import dto.LinkUpdateRequest;
import edu.java.client.GitHubRepositoriesClient;
import edu.java.configuration.ResourcesConfig;
import edu.java.dto.GitHubRepositoryRequest;
import edu.java.dto.LinkDTO;
import edu.java.repository.LinksRepository;
import edu.java.repository.UsersLinksRepository;
import edu.java.service.BotUpdateSender;
import edu.java.service.UpdateChecker;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class GithubUpdateChecker implements UpdateChecker {
    private final LinksRepository linksRepository;
    private final UsersLinksRepository usersLinksRepository;
    private final Pattern pattern;
    @Autowired private GitHubRepositoriesClient gitHubRepositoriesClient;
    @Autowired private BotUpdateSender updateSender;

    public GithubUpdateChecker(
        ResourcesConfig resourcesConfig, LinksRepository linksRepository, UsersLinksRepository usersLinksRepository
    ) {
        String patternString = resourcesConfig.supportedResources().get("github.com").urlPattern();
        this.pattern = Pattern.compile(patternString);
        this.linksRepository = linksRepository;
        this.usersLinksRepository = usersLinksRepository;
    }

    @Override
    @Transactional
    public void checkForUpdates(LinkDTO link) {
        this.gitHubRepositoriesClient
            .fetch(this.createResourceRequest(link.url()))
            .subscribe(
                response -> {
                    this.linksRepository.setLastActivity(link.url(), response.updatedAt());
                    this.updateSender.sendUpdate(new LinkUpdateRequest(
                        link.id(),
                        URI.create(link.url()),
                        "New activity in GitHub repo",
                        this.usersLinksRepository.findAllUsersByLink(link.id())
                    ));
                }
            );
    }

    @Override
    @Transactional
    public void setInfoFirstTime(LinkDTO link) {
        this.gitHubRepositoriesClient
            .fetch(this.createResourceRequest(link.url()))
            .subscribe(response -> this.linksRepository.setLastActivity(link.url(), response.updatedAt()));
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
