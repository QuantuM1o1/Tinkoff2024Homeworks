package edu.java.bot.configuration;

import edu.java.bot.bot.Bot;
import edu.java.bot.bot.TelegramBotImpl;
import edu.java.bot.userMessages.UserMessageProcessor;
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
        return new TelegramBotImpl(applicationConfig, userMessageProcessor);
    }
}
