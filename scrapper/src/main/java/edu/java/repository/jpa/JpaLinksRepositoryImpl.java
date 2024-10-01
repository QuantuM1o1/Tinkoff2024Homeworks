package edu.java.repository.jpa;

import edu.java.dto.LinkDTO;
import edu.java.entity.LinkEntity;
import edu.java.repository.LinksRepository;
import edu.java.repository.jpa.interfaces.JpaLinksRepository;
import edu.java.repository.jpa.interfaces.JpaLinksSitesRepository;
import org.springframework.stereotype.Repository;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Repository
public class JpaLinksRepositoryImpl implements LinksRepository {
    private JpaLinksRepository jpaLinksRepository;

    private JpaLinksSitesRepository jpaLinksSitesRepository;

    @Override
    public void addLink(String url, OffsetDateTime lastActivity, int siteId, int answerCount, int commentCount) {
        LinkEntity linkEntity = new LinkEntity();
        linkEntity.setUrl(url);
        linkEntity.setLastActivity(lastActivity);
        linkEntity.setSiteId(siteId);
        linkEntity.setAnswerCount(answerCount);
        linkEntity.setCommentCount(commentCount);
        this.jpaLinksRepository.saveAndFlush(linkEntity);
    }

    @Override
    public void removeLink(String url) {
        LinkEntity linkEntity = this.jpaLinksRepository.getByUrl(url);
        this.jpaLinksRepository.delete(linkEntity);
    }

    @Override
    public List<LinkDTO> findAllLinks() {
        List<LinkEntity> linkEntities = this.jpaLinksRepository.findAll();
        List<LinkDTO> list = new ArrayList<>();
        linkEntities.forEach(linkEntity -> list.add(new LinkDTO(
            linkEntity.getId(),
            linkEntity.getUrl(),
            linkEntity.getUpdatedAt(),
            linkEntity.getLastActivity(),
            linkEntity.getAnswerCount(),
            linkEntity.getCommentCount(),
            this.jpaLinksSitesRepository.getReferenceById(linkEntity.getId()).getDomainName()
        )));

        return list;
    }

    @Override
    public List<LinkDTO> findLinkByUrl(String url) {
        LinkEntity linkEntity = this.jpaLinksRepository.getByUrl(url);
        List<LinkDTO> list = new ArrayList<>();
        list.add(new LinkDTO(
            linkEntity.getId(),
            linkEntity.getUrl(),
            linkEntity.getUpdatedAt(),
            linkEntity.getLastActivity(),
            linkEntity.getAnswerCount(),
            linkEntity.getCommentCount(),
            this.jpaLinksSitesRepository.getReferenceById(linkEntity.getId()).getDomainName()
        ));

        return list;
    }

    @Override
    public List<LinkDTO> findNLinksLastUpdated(int n) {
        List<LinkEntity> linkEntities = this.jpaLinksRepository.findAllByOrderByUpdatedAt();
        List<LinkDTO> list = new ArrayList<>();
        IntStream.range(0, n).forEach(i -> list.add(new LinkDTO(
            linkEntities.get(i).getId(),
            linkEntities.get(i).getUrl(),
            linkEntities.get(i).getUpdatedAt(),
            linkEntities.get(i).getLastActivity(),
            linkEntities.get(i).getAnswerCount(),
            linkEntities.get(i).getCommentCount(),
            this.jpaLinksSitesRepository.getReferenceById(linkEntities.get(i).getId()).getDomainName()
        )));

        return list;
    }

    @Override
    public void setUpdatedAt(String url, OffsetDateTime updatedAt) {
        LinkEntity linkEntity = this.jpaLinksRepository.getByUrl(url);
        linkEntity.setUpdatedAt(updatedAt);
        this.jpaLinksRepository.saveAndFlush(linkEntity);
    }

    @Override
    public void setAnswerCount(String url, int count) {
        LinkEntity linkEntity = this.jpaLinksRepository.getByUrl(url);
        linkEntity.setAnswerCount(count);
        this.jpaLinksRepository.saveAndFlush(linkEntity);
    }

    @Override
    public void setCommentCount(String url, int count) {
        LinkEntity linkEntity = this.jpaLinksRepository.getByUrl(url);
        linkEntity.setCommentCount(count);
        this.jpaLinksRepository.saveAndFlush(linkEntity);
    }

    @Override
    public void setLastActivity(String url, OffsetDateTime lastActivity) {
        LinkEntity linkEntity = this.jpaLinksRepository.getByUrl(url);
        linkEntity.setLastActivity(lastActivity);
        this.jpaLinksRepository.saveAndFlush(linkEntity);
    }
}
