package edu.java.bot.dao;

import edu.java.bot.dto.ChatUser;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

@Repository
public class UsersMap {
    private final Map<Long, ChatUser> map;

    public Map<Long, ChatUser> getMap() {
        return map;
    }

    public UsersMap() {
        this.map = new HashMap<>();
    }
}
