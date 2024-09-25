package edu.java.repository.jooq;

import edu.java.dto.UserDTO;
import edu.java.repository.UsersRepository;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Users;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.UsersRecord;

@Repository
public class JooqUsersRepository extends Users implements UsersRepository {
    private final DSLContext dslContext;

    @Autowired
    public JooqUsersRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    @Transactional
    public void addUser(long chatId) {
        OffsetDateTime currentTime = OffsetDateTime.now();

        this.dslContext.insertInto(USERS)
            .set(USERS.CHAT_ID, chatId)
            .set(USERS.ADDED_AT, currentTime)
            .execute();
    }

    @Override
    @Transactional
    public void removeUser(long chatId) {
        OffsetDateTime currentTime = OffsetDateTime.now();

        this.dslContext.update(USERS)
            .set(USERS.DELETED_AT, currentTime)
            .where(USERS.CHAT_ID.eq(chatId))
            .execute();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAllUsers() {
        List<UsersRecord> records = this.dslContext.selectFrom(USERS)
            .where(USERS.DELETED_AT.isNull())
            .fetchInto(UsersRecord.class);
        List<UserDTO> list = new ArrayList<>();
        records.forEach(usersRecord -> list.add(new UserDTO(
            usersRecord.getChatId(),
            usersRecord.getAddedAt()
        )));

        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findUserById(long chatId) {
        List<UsersRecord> records = this.dslContext.selectFrom(USERS)
            .where(USERS.DELETED_AT.isNull().and(USERS.CHAT_ID.eq(chatId)))
            .fetchInto(UsersRecord.class);
        List<UserDTO> list = new ArrayList<>();
        records.forEach(usersRecord -> list.add(new UserDTO(
            usersRecord.getChatId(),
            usersRecord.getAddedAt()
        )));

        return list;
    }
}
