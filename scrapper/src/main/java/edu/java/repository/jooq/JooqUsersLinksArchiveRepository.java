package edu.java.repository.jooq;

import edu.java.repository.UsersLinksArchiveRepository;
import edu.java.scrapper.domain.jooq.tables.UsersLinksArchive;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.OffsetDateTime;

public class JooqUsersLinksArchiveRepository extends UsersLinksArchive implements UsersLinksArchiveRepository {
    private final DSLContext dslContext;

    @Autowired
    public JooqUsersLinksArchiveRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public void addUserLink(long chatId, long linkId) {
        OffsetDateTime currentTime = OffsetDateTime.now();

        this.dslContext.insertInto(USERS_LINKS_ARCHIVE)
            .set(USERS_LINKS_ARCHIVE.USER_ID, chatId)
            .set(USERS_LINKS_ARCHIVE.LINK_ID, linkId)
            .set(USERS_LINKS_ARCHIVE.ADDED_AT, currentTime)
            .execute();
    }

    @Override
    public void removeUserLink(long chatId, long linkId) {
        OffsetDateTime currentTime = OffsetDateTime.now();

        this.dslContext.update(USERS_LINKS_ARCHIVE)
            .set(USERS_LINKS_ARCHIVE.DELETED_AT, currentTime)
            .where(USERS_LINKS_ARCHIVE.USER_ID.eq(chatId)
                .and(USERS_LINKS_ARCHIVE.LINK_ID.eq(linkId)
                    .and(USERS_LINKS_ARCHIVE.DELETED_AT.isNotNull())))
            .execute();
    }
}
