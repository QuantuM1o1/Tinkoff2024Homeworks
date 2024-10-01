package edu.java.repository.jooq;

import edu.java.dto.LinkDTO;
import edu.java.repository.UsersLinksRepository;
import edu.java.scrapper.domain.jooq.tables.UsersLinks;
import edu.java.scrapper.domain.jooq.tables.records.LinksRecord;
import edu.java.scrapper.domain.jooq.tables.records.UsersRecord;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import static edu.java.scrapper.domain.jooq.Tables.LINKS;
import static edu.java.scrapper.domain.jooq.Tables.USERS;

@Repository
public class JooqUsersLinksRepository extends UsersLinks implements UsersLinksRepository {
    private final DSLContext dslContext;

    @Autowired
    public JooqUsersLinksRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public void addUserLink(long chatId, long linkId) {
        this.dslContext.insertInto(USERS_LINKS)
            .set(USERS_LINKS.USER_ID, chatId)
            .set(USERS_LINKS.LINK_ID, linkId)
            .execute();
    }

    @Override
    public void removeUserLink(long chatId, long linkId) {
        OffsetDateTime currentTime = OffsetDateTime.now();

        this.dslContext.delete(USERS_LINKS)
            .where(USERS_LINKS.USER_ID.eq(chatId).and(USERS_LINKS.LINK_ID.eq(linkId)))
            .execute();
    }

    @Override
    public List<LinkDTO> findAllLinksByUser(long chatId) {
        List<LinksRecord> records = this.dslContext.select(LINKS.fields())
            .from(USERS_LINKS)
            .innerJoin(LINKS).on(USERS_LINKS.LINK_ID.eq(LINKS.ID))
            .where(USERS_LINKS.USER_ID.eq(chatId))
            .fetchInto(LinksRecord.class);
        List<LinkDTO> list = new ArrayList<>();
        for (LinksRecord linksRecord : records) {
            String domain = this.dslContext.select(LINKS.linksSites().DOMAIN_NAME)
                .from(LINKS.linksSites())
                .where(LINKS.linksSites().ID.eq(linksRecord.getSiteId()))
                .fetchOneInto(String.class);
            list.add(new LinkDTO(
                linksRecord.getId(),
                linksRecord.getUrl(),
                linksRecord.getUpdatedAt(),
                linksRecord.getLastActivity(),
                linksRecord.getAnswerCount(),
                linksRecord.getCommentCount(),
                domain
            ));
        }

        return list;
    }

    @Override
    public List<Long> findAllUsersByLink(long linkId) {
        List<UsersRecord> records = this.dslContext.select(USERS.fields())
            .from(USERS_LINKS)
            .innerJoin(USERS).on(USERS_LINKS.USER_ID.eq(USERS.TG_CHAT_ID))
            .where(USERS_LINKS.LINK_ID.eq(linkId))
            .fetchInto(UsersRecord.class);
        List<Long> list = new ArrayList<>();
        records.forEach(usersRecord -> list.add(usersRecord.getTgChatId()));

        return list;
    }

    @Override
    public List<Long> findUserLink(long chatId, long linkId) {
        List<UsersRecord> records = this.dslContext.select(USERS.fields())
            .from(USERS_LINKS)
            .innerJoin(USERS).on(USERS_LINKS.USER_ID.eq(USERS.TG_CHAT_ID))
            .where(USERS_LINKS.LINK_ID.eq(linkId)
                .and(USERS_LINKS.USER_ID.eq(chatId)))
            .fetchInto(UsersRecord.class);
        List<Long> list = new ArrayList<>();
        records.forEach(usersRecord -> list.add(usersRecord.getTgChatId()));

        return list;
    }
}
