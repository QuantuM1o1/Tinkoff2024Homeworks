package edu.java.configuration;

import edu.java.repository.jdbc.JdbcLinksArchiveRepository;
import edu.java.repository.jdbc.JdbcLinksRepository;
import edu.java.repository.jdbc.JdbcUsersLinksArchiveRepository;
import edu.java.repository.jdbc.JdbcUsersLinksRepository;
import edu.java.repository.jdbc.JdbcUsersRepository;
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
        JdbcLinksRepository linksRepository, JdbcLinksArchiveRepository linksArchiveRepository,
        JdbcUsersLinksRepository usersLinksRepository, JdbcUsersLinksArchiveRepository usersLinksArchiveRepository
    ) {
        return new LinkService(
            linksRepository, linksArchiveRepository,
            usersLinksRepository, usersLinksArchiveRepository
        );
    }

    @Bean
    public LinkUpdaterService linkUpdaterService(
        JdbcLinksRepository linkRepository
    ) {
        return new LinkUpdaterService(linkRepository);
    }

    @Bean
    public TgChatService tgChatService(
        JdbcUsersRepository userRepository
    ) {
        return new TgChatService(userRepository);
    }

    @Bean(name = "github.com")
    public GithubUpdateChecker githubUpdateChecker(ResourcesConfig resourcesConfig) {
        return new GithubUpdateChecker(resourcesConfig);
    }

    @Bean(name = "stackoverflow.com")
    public StackOverflowUpdateChecker stackOverflowUpdateChecker(ResourcesConfig resourcesConfig) {
        return new StackOverflowUpdateChecker(resourcesConfig);
    }
}
