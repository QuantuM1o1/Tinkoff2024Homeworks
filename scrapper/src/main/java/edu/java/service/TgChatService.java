package edu.java.service;

import edu.java.apiException.AlreadyRegisteredException;
import edu.java.repository.UsersArchiveRepository;
import edu.java.repository.UsersRepository;
import org.springframework.transaction.annotation.Transactional;

public class TgChatService {
    private final UsersRepository usersRepository;

    private final UsersArchiveRepository usersArchiveRepository;

    public TgChatService(UsersRepository usersRepository, UsersArchiveRepository usersArchiveRepository) {
        this.usersRepository = usersRepository;
        this.usersArchiveRepository = usersArchiveRepository;
    }

    @Transactional
    public void register(long tgChatId) throws AlreadyRegisteredException {
        if (!this.usersRepository.findUserById(tgChatId).isEmpty()) {
            throw new AlreadyRegisteredException();
        } else {
            this.usersRepository.addUser(tgChatId);
            this.usersArchiveRepository.addUser(tgChatId);
        }
    }

    @Transactional
    public void unregister(long tgChatId) {
        this.usersRepository.removeUser(tgChatId);
        this.usersArchiveRepository.removeUser(tgChatId);
    }
}
