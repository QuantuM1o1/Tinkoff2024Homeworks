package edu.java.configuration;

import edu.java.repository.JpaLinkRepository;
import edu.java.repository.JpaUserRepository;
import edu.java.service.LinkService;
import edu.java.service.LinkUpdaterService;
import edu.java.service.TgChatService;
import edu.java.service.jpa.JpaLinkService;
import edu.java.service.jpa.JpaLinkUpdaterService;
import edu.java.service.jpa.JpaTgChatService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfig {
    @Bean
    public LinkService linkService(
        JpaLinkRepository linkRepository,
        JpaUserRepository userRepository
    ) {
        return new JpaLinkService(linkRepository, userRepository);
    }

    @Bean
    public LinkUpdaterService linkUpdaterService(
        JpaLinkRepository linkRepository
    ) {
        return new JpaLinkUpdaterService(linkRepository);
    }

    @Bean
    public TgChatService tgChatService(
        JpaUserRepository userRepository
    ) {
        return new JpaTgChatService(userRepository);
    }
}
