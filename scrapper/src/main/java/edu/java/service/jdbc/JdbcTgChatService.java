package edu.java.service.jdbc;

import edu.java.dao.JdbcUserDAO;
import edu.java.service.TgChatService;

public class JdbcTgChatService implements TgChatService {
    private final JdbcUserDAO jdbcUserRepository;

    public JdbcTgChatService(JdbcUserDAO jdbcUserRepository) {
        this.jdbcUserRepository = jdbcUserRepository;
    }

    @Override
    public void register(long tgChatId) {
        jdbcUserRepository.addUser(tgChatId);
    }

    @Override
    public void unregister(long tgChatId) {
        jdbcUserRepository.removeUser(tgChatId);
    }

    @Override
    public boolean checkIfAlreadyRegistered(long tgChatId) {
        return !jdbcUserRepository.findUserById(tgChatId).isEmpty();
    }

}
