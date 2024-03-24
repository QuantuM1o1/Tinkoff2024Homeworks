package edu.java.configuration;

import edu.java.dao.JdbcLinkDAO;
import edu.java.dao.JdbcUserDAO;
import edu.java.dao.JdbcUserLinkDAO;
import edu.java.service.LinkService;
import edu.java.service.LinkUpdaterService;
import edu.java.service.TgChatService;
import edu.java.service.jdbc.JdbcLinkService;
import edu.java.service.jdbc.JdbcLinkUpdaterService;
import edu.java.service.jdbc.JdbcTgChatService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfig {
    @Bean
    public LinkService linkService(
        JdbcLinkDAO linkRepository,
        JdbcUserLinkDAO userLinkRepository
    ) {
        return new JdbcLinkService(linkRepository, userLinkRepository);
    }

    @Bean
    public LinkUpdaterService linkUpdaterService(
        JdbcLinkDAO linkRepository
    ) {
        return new JdbcLinkUpdaterService(linkRepository);
    }

    @Bean
    public TgChatService tgChatService(
        JdbcUserDAO userRepository
    ) {
        return new JdbcTgChatService(userRepository);
    }
}
