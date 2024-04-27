package edu.java.configuration;

import edu.java.repository.jpa.JpaLinkRepositoryImpl;
import edu.java.repository.jpa.JpaUserLinkRepositoryImpl;
import edu.java.repository.jpa.JpaUserRepositoryImpl;
import edu.java.service.LinkService;
import edu.java.service.LinkUpdaterService;
import edu.java.service.TgChatService;
import edu.java.service.updateChecker.GithubUpdateChecker;
import edu.java.service.updateChecker.StackOverflowUpdateChecker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfig {
    @Bean
    public LinkService linkService(
        JpaLinkRepositoryImpl linkRepository,
        JpaUserLinkRepositoryImpl userLinkRepository
    ) {
        return new LinkService(linkRepository, userLinkRepository);
    }

    @Bean
    public LinkUpdaterService linkUpdaterService(
        JpaLinkRepositoryImpl linkRepository
    ) {
        return new LinkUpdaterService(linkRepository);
    }

    @Bean
    public TgChatService tgChatService(
        JpaUserRepositoryImpl userRepository
    ) {
        return new TgChatService(userRepository);
    }

    @Bean(name = "github.com")
    public GithubUpdateChecker githubUpdateChecker(
        JpaLinkRepositoryImpl linkRepository, ResourcesConfig resourcesConfig
    ) {
        return new GithubUpdateChecker(linkRepository, resourcesConfig);
    }

    @Bean(name = "stackoverflow.com")
    public StackOverflowUpdateChecker stackOverflowUpdateChecker(
        JpaLinkRepositoryImpl linkRepository, ResourcesConfig resourcesConfig
    ) {
        return new StackOverflowUpdateChecker(linkRepository, resourcesConfig);
    }
}
