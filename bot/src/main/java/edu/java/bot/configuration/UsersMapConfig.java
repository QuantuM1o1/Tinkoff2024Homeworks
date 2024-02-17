package edu.java.bot.configuration;

import edu.java.bot.dto.ChatUser;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsersMapConfig {
    @Bean
    public Map<Long, ChatUser> usersMap() {
        return new HashMap<>();
    }
}
