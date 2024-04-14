package edu.java.repository;

import edu.java.dto.LinkDTO;
import java.util.List;

public interface UserLinkRepository {
    void addUserLink(long chatId, long linkId);

    void removeUserLink(long chatId, long linkId);

    List<LinkDTO> findAllLinksByUser(long chatId);

    List<Long> findAllUsersByLink(long linkId);

    List<Long> findUserLink(long chatId, long linkId);
}
