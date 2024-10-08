package edu.java.configuration;

import edu.java.repository.jooq.JooqLinksRepository;
import edu.java.repository.jooq.JooqUsersArchiveRepository;
import edu.java.repository.jooq.JooqUsersLinksArchiveRepository;
import edu.java.repository.jooq.JooqUsersLinksRepository;
import edu.java.repository.jooq.JooqUsersRepository;
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
        JooqLinksRepository linkRepository,
        JooqUsersLinksRepository userLinkRepository,
        JooqUsersLinksArchiveRepository userLinkArchiveRepository
    ) {
        return new LinkService(linkRepository, userLinkRepository, userLinkArchiveRepository);
    }

    @Bean
    public LinkUpdaterService linkUpdaterService(JooqLinksRepository linkRepository) {
        return new LinkUpdaterService(linkRepository);
    }

    @Bean
    public TgChatService tgChatService(
        JooqUsersRepository userRepository, JooqUsersArchiveRepository usersArchiveRepository
    ) {
        return new TgChatService(userRepository, usersArchiveRepository);
    }

    @Bean(name = "github.com")
    public GithubUpdateChecker githubUpdateChecker(
        ResourcesConfig resourcesConfig,
        JooqLinksRepository linkRepository,
        JooqUsersLinksRepository userLinkRepository
    ) {
        return new GithubUpdateChecker(resourcesConfig, linkRepository, userLinkRepository);
    }

    @Bean(name = "stackoverflow.com")
    public StackOverflowUpdateChecker stackOverflowUpdateChecker(
        ResourcesConfig resourcesConfig,
        JooqLinksRepository linkRepository,
        JooqUsersLinksRepository userLinkRepository
    ) {
        return new StackOverflowUpdateChecker(resourcesConfig, linkRepository, userLinkRepository);
    }
}
