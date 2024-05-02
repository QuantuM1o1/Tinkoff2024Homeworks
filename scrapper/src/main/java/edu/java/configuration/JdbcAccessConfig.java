package edu.java.configuration;

import edu.java.repository.jdbc.JdbcLinkRepository;
import edu.java.repository.jdbc.JdbcUserLinkRepository;
import edu.java.repository.jdbc.JdbcUserRepository;
import edu.java.service.LinkService;
import edu.java.service.LinkUpdaterService;
import edu.java.service.TgChatService;
import edu.java.service.updateChecker.GithubUpdateChecker;
import edu.java.service.updateChecker.StackOverflowUpdateChecker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfig {
    @Bean
    public LinkService linkService(
        JdbcLinkRepository linkRepository,
        JdbcUserLinkRepository userLinkRepository
    ) {
        return new LinkService(linkRepository, userLinkRepository);
    }

    @Bean
    public LinkUpdaterService linkUpdaterService(
        JdbcLinkRepository linkRepository
    ) {
        return new LinkUpdaterService(linkRepository);
    }

    @Bean
    public TgChatService tgChatService(
        JdbcUserRepository userRepository
    ) {
        return new TgChatService(userRepository);
    }

    @Bean(name = "github.com")
    public GithubUpdateChecker githubUpdateChecker(
        JdbcLinkRepository linkRepository, ResourcesConfig resourcesConfig
    ) {
        return new GithubUpdateChecker(linkRepository, resourcesConfig);
    }

    @Bean(name = "stackoverflow.com")
    public StackOverflowUpdateChecker stackOverflowUpdateChecker(
        JdbcLinkRepository linkRepository, ResourcesConfig resourcesConfig
    ) {
        return new StackOverflowUpdateChecker(linkRepository, resourcesConfig);
    }
}
