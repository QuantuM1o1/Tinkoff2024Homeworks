package edu.java.configuration;

import edu.java.dao.jooq.JooqLinkDAO;
import edu.java.dao.jooq.JooqUserDAO;
import edu.java.dao.jooq.JooqUserLinkDAO;
import edu.java.service.LinkService;
import edu.java.service.LinkUpdaterService;
import edu.java.service.TgChatService;
import edu.java.service.jooq.JooqLinkService;
import edu.java.service.jooq.JooqLinkUpdaterService;
import edu.java.service.jooq.JooqTgChatService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqAccessConfig {
    @Bean
    public LinkService linkService(
        JooqLinkDAO linkRepository,
        JooqUserLinkDAO userLinkRepository
    ) {
        return new JooqLinkService(linkRepository, userLinkRepository);
    }

    @Bean
    public LinkUpdaterService linkUpdaterService(
        JooqLinkDAO linkRepository
    ) {
        return new JooqLinkUpdaterService(linkRepository);
    }

    @Bean
    public TgChatService tgChatService(
        JooqUserDAO userRepository
    ) {
        return new JooqTgChatService(userRepository);
    }
}
