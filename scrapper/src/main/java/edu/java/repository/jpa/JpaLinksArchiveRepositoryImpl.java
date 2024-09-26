package edu.java.repository.jpa;

import edu.java.entity.LinkArchiveEntity;
import edu.java.repository.LinksArchiveRepository;
import edu.java.repository.jpa.interfaces.JpaLinksArchiveRepository;
import java.time.OffsetDateTime;

public class JpaLinksArchiveRepositoryImpl implements LinksArchiveRepository {
    private JpaLinksArchiveRepository jpaLinksArchiveRepository;

    @Override
    public void addLink(long linkId) {
        LinkArchiveEntity linkArchiveEntity = new LinkArchiveEntity();
        linkArchiveEntity.setLinkId(linkId);
        linkArchiveEntity.setAddedAt(OffsetDateTime.now());
        this.jpaLinksArchiveRepository.saveAndFlush(linkArchiveEntity);
    }

    @Override
    public void removeLink(long linkId) {
        OffsetDateTime currentTime = OffsetDateTime.now();

        this.jpaLinksArchiveRepository.markAsDeleted(currentTime, linkId);
    }
}
