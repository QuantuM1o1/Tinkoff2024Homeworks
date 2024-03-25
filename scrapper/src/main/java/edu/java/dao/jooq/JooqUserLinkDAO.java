package edu.java.dao.jooq;

import edu.java.dao.UserLinkDAO;
import edu.java.dto.LinkDTO;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.UsersLinks;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.LinksRecord;
import static ru.tinkoff.edu.java.scrapper.domain.jooq.Tables.LINKS;

@Repository
public class JooqUserLinkDAO extends UsersLinks implements UserLinkDAO {
    private final DSLContext dslContext;

    public JooqUserLinkDAO(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    @Transactional
    public void addUserLink(Long chatId, Long linkId) {
        this.dslContext.insertInto(USERS_LINKS)
            .set(USERS_LINKS.USER_ID, chatId)
            .set(USERS_LINKS.LINK_ID, linkId)
            .execute();
    }

    @Override
    @Transactional
    public void removeUserLink(Long chatId, Long linkId) {
        OffsetDateTime currentTime = OffsetDateTime.now();

        this.dslContext.update(USERS_LINKS)
            .set(USERS_LINKS.DELETED_AT, currentTime)
            .where(USERS_LINKS.USER_ID.eq(chatId).and(USERS_LINKS.LINK_ID.eq(linkId)))
            .execute();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LinkDTO> findAllLinksByUser(Long chatId) {
        List<LinksRecord> records = this.dslContext.select(LINKS.fields())
            .from(USERS_LINKS)
            .innerJoin(LINKS).on(USERS_LINKS.LINK_ID.eq(LINKS.LINK_ID))
            .where(USERS_LINKS.USER_ID.eq(chatId).and(USERS_LINKS.DELETED_AT.isNull()))
            .fetchInto(LinksRecord.class);
        List<LinkDTO> list = new ArrayList<>();
        records.forEach(linksRecord -> list.add(new LinkDTO(
            linksRecord.getLinkId(),
            linksRecord.getUrl(),
            linksRecord.getAddedAt(),
            linksRecord.getUpdatedAt(),
            linksRecord.getLastActivity(),
            linksRecord.getSiteId(),
            linksRecord.getAnswerCount(),
            linksRecord.getCommentCount()
        )));

        return list;
    }
}
