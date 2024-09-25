package edu.java.repository;

import edu.java.dto.LinkDTO;
import java.time.OffsetDateTime;
import java.util.List;

public interface LinksRepository {
    void addLink(String url, OffsetDateTime lastActivity, int siteId, int answerCount, int commentCount);

    void removeLink(String url);

    List<LinkDTO> findAllLinks();

    List<LinkDTO> findLinkByUrl(String url);

    List<LinkDTO> findNLinksLastUpdated(int n);

    void setUpdatedAt(String url, OffsetDateTime updatedAt);

    void setAnswerCount(String url, int count);

    void setCommentCount(String url, int count);

    void setLastActivity(String url, OffsetDateTime lastActivity);
}
