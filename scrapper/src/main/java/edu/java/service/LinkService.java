package edu.java.service;

import edu.java.dto.LinkDTO;
import java.util.Collection;

public interface LinkService {
    void add(long tgChatId, String url);

    void remove(long tgChatId, String url);

    Collection<LinkDTO> listAll(long tgChatId);

    Collection<Long> findAllUsersForLink(long linkId);
}
