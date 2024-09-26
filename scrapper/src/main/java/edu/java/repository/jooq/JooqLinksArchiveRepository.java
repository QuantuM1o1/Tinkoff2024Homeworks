package edu.java.repository.jooq;

import edu.java.repository.LinksArchiveRepository;
import edu.java.scrapper.domain.jooq.tables.LinksArchive;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.time.OffsetDateTime;

@Repository
public class JooqLinksArchiveRepository extends LinksArchive implements LinksArchiveRepository {
    private final DSLContext dslContext;

    @Autowired
    public JooqLinksArchiveRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public void addLink(long linkId) {
        OffsetDateTime currentTime = OffsetDateTime.now();

        this.dslContext.insertInto(LINKS_ARCHIVE)
            .set(LINKS_ARCHIVE.LINK_ID, linkId)
            .set(LINKS_ARCHIVE.ADDED_AT, currentTime)
            .execute();
    }

    @Override
    public void removeLink(long linkId) {
        OffsetDateTime currentTime = OffsetDateTime.now();

        this.dslContext.update(LINKS_ARCHIVE)
            .set(LINKS_ARCHIVE.DELETED_AT, currentTime)
            .where(LINKS_ARCHIVE.LINK_ID.eq(linkId).and(LINKS_ARCHIVE.DELETED_AT.isNotNull()))
            .execute();
    }
}
