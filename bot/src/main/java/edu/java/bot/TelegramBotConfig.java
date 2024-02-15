package edu.java.bot;

import edu.java.bot.UserMessages.UserMessageProcessor;
import edu.java.bot.configuration.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramBotConfig {

    private final UserMessageProcessor userMessageProcessor;

    @Autowired
    public TelegramBotConfig(UserMessageProcessor userMessageProcessor) {
        this.userMessageProcessor = userMessageProcessor;
    }

    @Bean
    public Bot bot(ApplicationConfig applicationConfig) {
        // Create and return an instance of TelegramBotImpl with the provided auth token
        return new TelegramBotImpl(applicationConfig, userMessageProcessor);
    }
}
