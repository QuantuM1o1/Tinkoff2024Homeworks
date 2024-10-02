package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramConfig {
    @Bean
    @Autowired
    public TelegramBot bot(ApplicationConfig applicationConfig) {
        return new TelegramBot(applicationConfig.telegramToken());
    }
}
