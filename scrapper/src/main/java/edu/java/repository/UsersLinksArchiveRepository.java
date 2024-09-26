package edu.java.repository;

public interface UsersLinksArchiveRepository {
    void addUserLink(long chatId, long linkId);

    void removeUserLink(long chatId, long linkId);
}
