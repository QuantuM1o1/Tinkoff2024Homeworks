package edu.java.service;

import edu.java.apiException.AlreadyRegisteredException;
import edu.java.repository.jdbc.JdbcUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TgChatService {
    @Autowired
    private JdbcUserRepository userRepository;

    @Transactional
    public void register(long tgChatId) throws AlreadyRegisteredException {
        if (!this.userRepository.findUserById(tgChatId).isEmpty()) {
            throw new AlreadyRegisteredException();
        } else {
            this.userRepository.addUser(tgChatId);
        }
    }

    @Transactional
    public void unregister(long tgChatId) {
        this.userRepository.removeUser(tgChatId);
    }
}
