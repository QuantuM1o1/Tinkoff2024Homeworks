package edu.java.repository.jpa;

import edu.java.dto.LinkDTO;
import edu.java.entity.LinkEntity;
import edu.java.repository.JpaLinkRepository;
import edu.java.repository.JpaLinksSitesRepository;
import edu.java.repository.LinkRepository;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class JpaLinkRepositoryImpl implements LinkRepository {
    private JpaLinkRepository jpaLinkRepository;

    private JpaLinksSitesRepository jpaLinksSitesRepository;

    @Override
    public void addLink(String url, OffsetDateTime lastActivity, int siteId, int answerCount, int commentCount) {
        LinkEntity linkEntity = new LinkEntity();
        linkEntity.setUrl(url);
        linkEntity.setLastActivity(lastActivity);
        linkEntity.setSiteId(siteId);
        linkEntity.setAnswerCount(answerCount);
        linkEntity.setCommentCount(commentCount);
        this.jpaLinkRepository.saveAndFlush(linkEntity);
    }

    @Override
    public void removeLink(String url) {
        LinkEntity linkEntity = this.jpaLinkRepository.getByUrl(url);
        this.jpaLinkRepository.delete(linkEntity);
    }

    @Override
    public List<LinkDTO> findAllLinks() {
        List<LinkEntity> linkEntities = this.jpaLinkRepository.findAll();
        List<LinkDTO> list = new ArrayList<>();
        linkEntities.forEach(linkEntity -> list.add(new LinkDTO(
            linkEntity.getId(),
            linkEntity.getUrl(),
            linkEntity.getAddedAt(),
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
        LinkEntity linkEntity = this.jpaLinkRepository.getByUrl(url);
        List<LinkDTO> list = new ArrayList<>();
        list.add(new LinkDTO(
            linkEntity.getId(),
            linkEntity.getUrl(),
            linkEntity.getAddedAt(),
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
        List<LinkEntity> linkEntities = this.jpaLinkRepository.findAllByOrderByUpdatedAt();
        List<LinkDTO> list = new ArrayList<>();
        IntStream.range(0, n).forEach(i -> list.add(new LinkDTO(
            linkEntities.get(i).getId(),
            linkEntities.get(i).getUrl(),
            linkEntities.get(i).getAddedAt(),
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
        LinkEntity linkEntity = this.jpaLinkRepository.getByUrl(url);
        linkEntity.setUpdatedAt(updatedAt);
        this.jpaLinkRepository.saveAndFlush(linkEntity);
    }
}
