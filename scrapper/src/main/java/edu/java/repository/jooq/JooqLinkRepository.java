package edu.java.repository.jooq;

import edu.java.dto.LinkDTO;
import edu.java.repository.LinkRepository;
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
public class JooqLinkRepository extends Links implements LinkRepository {
    private final DSLContext dslContext;

    @Autowired
    public JooqLinkRepository(DSLContext dslContext) {
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
        for (LinksRecord linksRecord : records) {
            String domain = this.dslContext.select(LINKS.linksSites().DOMAIN_NAME)
                .from(LINKS.linksSites())
                .where(LINKS.linksSites().ID.eq(linksRecord.getSiteId()))
                .fetchOneInto(String.class);
            list.add(new LinkDTO(
                linksRecord.getLinkId(),
                linksRecord.getUrl(),
                linksRecord.getAddedAt(),
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
    @Transactional(readOnly = true)
    public List<LinkDTO> findLinkByUrl(String url) {
        List<LinksRecord> records = this.dslContext.selectFrom(LINKS)
            .where(LINKS.DELETED_AT.isNull().and(LINKS.URL.eq(url)))
            .fetchInto(LinksRecord.class);
        List<LinkDTO> list = new ArrayList<>();
        for (LinksRecord linksRecord : records) {
            String domain = this.dslContext.select(LINKS.linksSites().DOMAIN_NAME)
                .from(LINKS.linksSites())
                .where(LINKS.linksSites().ID.eq(linksRecord.getSiteId()))
                .fetchOneInto(String.class);
            list.add(new LinkDTO(
                linksRecord.getLinkId(),
                linksRecord.getUrl(),
                linksRecord.getAddedAt(),
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
    @Transactional(readOnly = true)
    public List<LinkDTO> findNLinksLastUpdated(int n) {
        List<LinksRecord> records = this.dslContext.selectFrom(LINKS)
            .where(LINKS.DELETED_AT.isNull())
            .orderBy(LINKS.UPDATED_AT.asc())
            .limit(n)
            .fetchInto(LinksRecord.class);
        List<LinkDTO> list = new ArrayList<>();
        for (LinksRecord linksRecord : records) {
            String domain = this.dslContext.select(LINKS.linksSites().DOMAIN_NAME)
                .from(LINKS.linksSites())
                .where(LINKS.linksSites().ID.eq(linksRecord.getSiteId()))
                .fetchOneInto(String.class);
            list.add(new LinkDTO(
                linksRecord.getLinkId(),
                linksRecord.getUrl(),
                linksRecord.getAddedAt(),
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
    public void setUpdatedAt(String url, OffsetDateTime updatedAt) {
        this.dslContext.update(LINKS)
            .set(LINKS.UPDATED_AT, updatedAt)
            .where(LINKS.URL.eq(url));
    }

    @Override
    public void setAnswerCount(String url, int count) {
        this.dslContext.update(LINKS)
            .set(LINKS.ANSWER_COUNT, count)
            .where(LINKS.URL.eq(url));
    }

    @Override
    public void setCommentCount(String url, int count) {
        this.dslContext.update(LINKS)
            .set(LINKS.COMMENT_COUNT, count)
            .where(LINKS.URL.eq(url));
    }

    @Override
    public void setLastActivity(String url, OffsetDateTime lastActivity) {
        this.dslContext.update(LINKS)
            .set(LINKS.LAST_ACTIVITY, lastActivity)
            .where(LINKS.URL.eq(url));
    }
}
