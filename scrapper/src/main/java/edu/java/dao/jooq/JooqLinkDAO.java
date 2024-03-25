package edu.java.dao.jooq;

import edu.java.dao.LinkDAO;
import edu.java.dto.LinkDTO;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Links;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.LinksRecord;

@Repository
public class JooqLinkDAO extends Links implements LinkDAO {
    private final DSLContext dslContext;

    @Autowired
    public JooqLinkDAO(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    @Transactional
    public void addLink(String url, OffsetDateTime lastActivity, int siteId, int answerCount, int commentCount) {
        OffsetDateTime currentTime = OffsetDateTime.now();

        this.dslContext.insertInto(LINKS)
            .set(LINKS.URL, url)
            .set(LINKS.ADDED_AT, currentTime)
            .set(LINKS.UPDATED_AT, currentTime)
            .set(LINKS.LAST_ACTIVITY, lastActivity)
            .set(LINKS.SITE_ID, siteId)
            .set(LINKS.ANSWER_COUNT, answerCount)
            .set(LINKS.COMMENT_COUNT, commentCount)
            .execute();
    }

    @Override
    @Transactional
    public void removeLink(String url) {
        OffsetDateTime currentTime = OffsetDateTime.now();

        this.dslContext.update(LINKS)
            .set(LINKS.DELETED_AT, currentTime)
            .where(LINKS.URL.equal(url))
            .execute();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LinkDTO> findAllLinks() {
        List<LinksRecord> records = this.dslContext.selectFrom(LINKS)
            .where(LINKS.DELETED_AT.isNull())
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

    @Override
    @Transactional(readOnly = true)
    public List<LinkDTO> findLinkByUrl(String url) {
        List<LinksRecord> records = this.dslContext.selectFrom(LINKS)
            .where(LINKS.DELETED_AT.isNull().and(LINKS.URL.eq(url)))
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

    @Override
    @Transactional(readOnly = true)
    public List<LinkDTO> findNLinksLastUpdated(int n) {
        List<LinksRecord> records = this.dslContext.selectFrom(LINKS)
            .where(LINKS.DELETED_AT.isNull())
            .orderBy(LINKS.UPDATED_AT.asc())
            .limit(n)
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
