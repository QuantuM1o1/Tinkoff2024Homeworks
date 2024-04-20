package edu.java.configuration;

import edu.java.repository.jooq.JooqLinkRepository;
import edu.java.repository.jooq.JooqUserLinkRepository;
import edu.java.repository.jooq.JooqUserRepository;
import edu.java.service.LinkService;
import edu.java.service.LinkUpdaterService;
import edu.java.service.TgChatService;
import edu.java.service.updateChecker.GithubUpdateChecker;
import edu.java.service.updateChecker.StackOverflowUpdateChecker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqAccessConfig {
    @Bean
    public LinkService linkService(
        JooqLinkRepository linkRepository,
        JooqUserLinkRepository userLinkRepository
    ) {
        return new LinkService(linkRepository, userLinkRepository);
    }

    @Bean
    public LinkUpdaterService linkUpdaterService(
        JooqLinkRepository linkRepository
    ) {
        return new LinkUpdaterService(linkRepository);
    }

    @Bean
    public TgChatService tgChatService(
        JooqUserRepository userRepository
    ) {
        return new TgChatService(userRepository);
    }

    @Bean(name = "github.com")
    public GithubUpdateChecker githubUpdateChecker(
        JooqLinkRepository linkRepository, ResourcesConfig resourcesConfig
    ) {
        return new GithubUpdateChecker(linkRepository, resourcesConfig);
    }

    @Bean(name = "stackoverflow.com")
    public StackOverflowUpdateChecker stackOverflowUpdateChecker(
        JooqLinkRepository linkRepository, ResourcesConfig resourcesConfig
    ) {
        return new StackOverflowUpdateChecker(linkRepository, resourcesConfig);
    }
}
