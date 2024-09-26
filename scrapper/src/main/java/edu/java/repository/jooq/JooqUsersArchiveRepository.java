package edu.java.repository.jooq;

import edu.java.repository.UsersArchiveRepository;
import edu.java.scrapper.domain.jooq.tables.UsersArchive;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.OffsetDateTime;

public class JooqUsersArchiveRepository extends UsersArchive implements UsersArchiveRepository {
    private final DSLContext dslContext;

    @Autowired
    public JooqUsersArchiveRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public void addUser(long chatId) {
        OffsetDateTime currentTime = OffsetDateTime.now();

        this.dslContext.insertInto(USERS_ARCHIVE)
            .set(USERS_ARCHIVE.TG_CHAT_ID, chatId)
            .set(USERS_ARCHIVE.ADDED_AT, currentTime)
            .execute();
    }

    @Override
    public void removeUser(long chatId) {
        OffsetDateTime currentTime = OffsetDateTime.now();

        this.dslContext.update(USERS_ARCHIVE)
            .set(USERS_ARCHIVE.DELETED_AT, currentTime)
            .where(USERS_ARCHIVE.TG_CHAT_ID.eq(chatId).and(USERS_ARCHIVE.DELETED_AT.isNotNull()))
            .execute();
    }
}
