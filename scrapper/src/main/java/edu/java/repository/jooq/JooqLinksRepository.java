package edu.java.repository.jooq;

import edu.java.dto.LinkDTO;
import edu.java.repository.LinksRepository;
import edu.java.scrapper.domain.jooq.tables.Links;
import edu.java.scrapper.domain.jooq.tables.records.LinksRecord;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class JooqLinksRepository extends Links implements LinksRepository {
    private final DSLContext dslContext;

    @Autowired
    public JooqLinksRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public void addLink(String url, OffsetDateTime lastActivity, int siteId, int answerCount, int commentCount) {
        OffsetDateTime currentTime = OffsetDateTime.now();

        this.dslContext.insertInto(LINKS)
            .set(LINKS.URL, url)
            .set(LINKS.UPDATED_AT, currentTime)
            .set(LINKS.LAST_ACTIVITY, lastActivity)
            .set(LINKS.SITE_ID, siteId)
            .set(LINKS.ANSWER_COUNT, answerCount)
            .set(LINKS.COMMENT_COUNT, commentCount)
            .execute();
    }

    @Override
    public void removeLink(String url) {
        OffsetDateTime currentTime = OffsetDateTime.now();

        this.dslContext.delete(LINKS)
            .where(LINKS.URL.equal(url))
            .execute();
    }

    @Override
    public List<LinkDTO> findAllLinks() {
        List<LinksRecord> records = this.dslContext.selectFrom(LINKS)
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
    public List<LinkDTO> findLinkByUrl(String url) {
        List<LinksRecord> records = this.dslContext.selectFrom(LINKS)
            .where(LINKS.URL.eq(url))
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
    public List<LinkDTO> findNLinksLastUpdated(int n) {
        List<LinksRecord> records = this.dslContext.selectFrom(LINKS)
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
    public void setUpdatedAt(String url, OffsetDateTime updatedAt) {
        this.dslContext.update(LINKS)
            .set(LINKS.UPDATED_AT, updatedAt)
            .where(LINKS.URL.eq(url))
            .execute();
    }

    @Override
    public void setAnswerCount(String url, int count) {
        this.dslContext.update(LINKS)
            .set(LINKS.ANSWER_COUNT, count)
            .where(LINKS.URL.eq(url))
            .execute();
    }

    @Override
    public void setCommentCount(String url, int count) {
        this.dslContext.update(LINKS)
            .set(LINKS.COMMENT_COUNT, count)
            .where(LINKS.URL.eq(url))
            .execute();
    }

    @Override
    public void setLastActivity(String url, OffsetDateTime lastActivity) {
        this.dslContext.update(LINKS)
            .set(LINKS.LAST_ACTIVITY, lastActivity)
            .where(LINKS.URL.eq(url))
            .execute();
    }
}
