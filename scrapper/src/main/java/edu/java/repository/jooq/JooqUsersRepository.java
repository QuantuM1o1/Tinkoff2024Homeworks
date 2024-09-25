package edu.java.repository.jooq;

import edu.java.dto.UserDTO;
import edu.java.repository.UsersRepository;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import edu.java.scrapper.domain.jooq.tables.Users;
import edu.java.scrapper.domain.jooq.tables.records.UsersRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
            .set(USERS.TG_CHAT_ID, chatId)
            .execute();
    }

    @Override
    @Transactional
    public void removeUser(long chatId) {
        OffsetDateTime currentTime = OffsetDateTime.now();

        this.dslContext.delete(USERS)
            .where(USERS.TG_CHAT_ID.eq(chatId))
            .execute();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAllUsers() {
        List<UsersRecord> records = this.dslContext.selectFrom(USERS)
            .fetchInto(UsersRecord.class);
        List<UserDTO> list = new ArrayList<>();
        records.forEach(usersRecord -> list.add(new UserDTO(usersRecord.getTgChatId())));

        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findUserById(long chatId) {
        List<UsersRecord> records = this.dslContext.selectFrom(USERS)
            .where(USERS.TG_CHAT_ID.eq(chatId))
            .fetchInto(UsersRecord.class);
        List<UserDTO> list = new ArrayList<>();
        records.forEach(usersRecord -> list.add(new UserDTO(usersRecord.getTgChatId())));

        return list;
    }
}
