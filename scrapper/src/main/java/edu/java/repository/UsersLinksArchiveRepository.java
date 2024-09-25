package edu.java.repository;

import edu.java.dto.LinkDTO;
import java.util.List;

public interface UsersLinksArchiveRepository {
    void addUserLink(long chatId, long linkId);

    void removeUserLink(long chatId, long linkId);
}
