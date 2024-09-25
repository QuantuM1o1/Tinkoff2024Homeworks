package edu.java.service;

import edu.java.apiException.AlreadyRegisteredException;
import edu.java.repository.UsersRepository;
import org.springframework.transaction.annotation.Transactional;

public class TgChatService {
    private final UsersRepository usersRepository;

    public TgChatService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Transactional
    public void register(long tgChatId) throws AlreadyRegisteredException {
        if (!this.usersRepository.findUserById(tgChatId).isEmpty()) {
            throw new AlreadyRegisteredException();
        } else {
            this.usersRepository.addUser(tgChatId);
        }
    }

    @Transactional
    public void unregister(long tgChatId) {
        this.usersRepository.removeUser(tgChatId);
    }
}
