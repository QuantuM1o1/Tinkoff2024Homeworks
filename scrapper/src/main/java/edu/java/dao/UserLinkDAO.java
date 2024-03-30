package edu.java.dao;

import edu.java.dto.LinkDTO;
import java.util.List;

public interface UserLinkDAO {
    void addUserLink(Long chatId, Long linkId);

    void removeUserLink(Long chatId, Long linkId);

    List<LinkDTO> findAllLinksByUser(Long chatId);

    List<Long> findAllUsersByLink(Long linkId);
}
