package edu.java.repository.jooq;

import edu.java.repository.UsersLinksArchiveRepository;
import edu.java.scrapper.domain.jooq.tables.UsersLinksArchive;
import java.time.OffsetDateTime;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class JooqUsersLinksArchiveRepository extends UsersLinksArchive implements UsersLinksArchiveRepository {
    private final DSLContext dslContext;

    @Autowired
    public JooqUsersLinksArchiveRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public void addUserLink(long chatId, String url) {
        OffsetDateTime currentTime = OffsetDateTime.now();

        this.dslContext.insertInto(USERS_LINKS_ARCHIVE)
            .set(USERS_LINKS_ARCHIVE.USER_ID, chatId)
            .set(USERS_LINKS_ARCHIVE.URL, url)
            .set(USERS_LINKS_ARCHIVE.ADDED_AT, currentTime)
            .execute();
    }

    @Override
    public void removeUserLink(long chatId, String url) {
        OffsetDateTime currentTime = OffsetDateTime.now();

        this.dslContext.update(USERS_LINKS_ARCHIVE)
            .set(USERS_LINKS_ARCHIVE.DELETED_AT, currentTime)
            .where(USERS_LINKS_ARCHIVE.USER_ID.eq(chatId)
                .and(USERS_LINKS_ARCHIVE.URL.eq(url)
                    .and(USERS_LINKS_ARCHIVE.DELETED_AT.isNotNull())))
            .execute();
    }
}
