package edu.java.configuration;

import edu.java.repository.jpa.JpaLinksRepositoryImpl;
import edu.java.repository.jpa.JpaUsersArchiveRepositoryImpl;
import edu.java.repository.jpa.JpaUsersLinksArchiveRepositoryImpl;
import edu.java.repository.jpa.JpaUsersLinksRepositoryImpl;
import edu.java.repository.jpa.JpaUsersRepositoryImpl;
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
        JpaLinksRepositoryImpl linkRepository,
        JpaUsersLinksRepositoryImpl userLinkRepository,
        JpaUsersLinksArchiveRepositoryImpl userLinkArchiveRepository
    ) {
        return new LinkService(linkRepository, userLinkRepository, userLinkArchiveRepository);
    }

    @Bean
    public LinkUpdaterService linkUpdaterService(JpaLinksRepositoryImpl linkRepository) {
        return new LinkUpdaterService(linkRepository);
    }

    @Bean
    public TgChatService tgChatService(
        JpaUsersRepositoryImpl userRepository, JpaUsersArchiveRepositoryImpl userArchiveRepository
    ) {
        return new TgChatService(userRepository, userArchiveRepository);
    }

    @Bean(name = "github.com")
    public GithubUpdateChecker githubUpdateChecker(
        ResourcesConfig resourcesConfig,
        JpaLinksRepositoryImpl linkRepository,
        JpaUsersLinksRepositoryImpl userLinkRepository
    ) {
        return new GithubUpdateChecker(resourcesConfig, linkRepository, userLinkRepository);
    }

    @Bean(name = "stackoverflow.com")
    public StackOverflowUpdateChecker stackOverflowUpdateChecker(
        ResourcesConfig resourcesConfig,
        JpaLinksRepositoryImpl linkRepository,
        JpaUsersLinksRepositoryImpl userLinkRepository
    ) {
        return new StackOverflowUpdateChecker(resourcesConfig, linkRepository, userLinkRepository);
    }
}
