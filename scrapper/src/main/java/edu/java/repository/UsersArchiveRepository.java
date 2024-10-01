package edu.java.repository;

public interface UsersArchiveRepository {
    void addUser(long chatId);

    void removeUser(long chatId);
}
