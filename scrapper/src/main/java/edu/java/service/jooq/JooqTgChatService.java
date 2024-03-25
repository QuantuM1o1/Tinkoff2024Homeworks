package edu.java.service.jooq;

import edu.java.dao.jooq.JooqUserDAO;
import edu.java.service.TgChatService;

public class JooqTgChatService implements TgChatService {
    private final JooqUserDAO jooqUserRepository;

    public JooqTgChatService(JooqUserDAO jooqUserRepository) {
        this.jooqUserRepository = jooqUserRepository;
    }

    @Override
    public void register(long tgChatId) {
        this.jooqUserRepository.addUser(tgChatId);
    }

    @Override
    public void unregister(long tgChatId) {
        this.jooqUserRepository.removeUser(tgChatId);
    }

    @Override
    public boolean checkIfAlreadyRegistered(long tgChatId) {
        return !this.jooqUserRepository.findUserById(tgChatId).isEmpty();
    }
}
