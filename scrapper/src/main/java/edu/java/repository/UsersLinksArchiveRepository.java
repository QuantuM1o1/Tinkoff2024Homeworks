package edu.java.repository;

public interface UsersLinksArchiveRepository {
    void addUserLink(long chatId, String url);

    void removeUserLink(long chatId, String url);
}
