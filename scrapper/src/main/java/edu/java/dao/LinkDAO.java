package edu.java.dao;

import edu.java.dto.LinkDTO;
import java.time.OffsetDateTime;
import java.util.List;

public interface LinkDAO {
    void addLink(String url, OffsetDateTime lastActivity, int siteId);

    void removeLink(String url);

    List<LinkDTO> findAllLinks();

    List<LinkDTO> findLinkByUrl(String url);

    List<LinkDTO> findNLinksLastUpdated(int n);
}
