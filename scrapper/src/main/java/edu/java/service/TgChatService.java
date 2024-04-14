package edu.java.service;

import edu.java.apiException.AlreadyRegisteredException;
import edu.java.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

public class TgChatService {
    private final UserRepository userRepository;

    public TgChatService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
