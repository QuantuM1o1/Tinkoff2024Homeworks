package edu.java.service.jdbc;

import edu.java.dao.JdbcUserDAO;
import edu.java.service.TgChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdbcTgChatService implements TgChatService {
    @Autowired
    JdbcUserDAO userRepository;

    @Override
    public void register(long tgChatId) {
        userRepository.addUser(tgChatId);
    }

    @Override
    public void unregister(long tgChatId) {
        userRepository.removeUser(tgChatId);
    }

    @Override
    public boolean checkIfAlreadyRegistered(long tgChatId) {
        return !userRepository.findUserById(tgChatId).isEmpty();
    }

}
