package edu.java.service.jpa;

import edu.java.client.GitHubRepositoriesClient;
import edu.java.client.StackOverflowQuestionClient;
import edu.java.dto.GitHubRepositoryResponse;
import edu.java.dto.LinkDTO;
import edu.java.dto.StackOverflowQuestionResponse;
import edu.java.entity.LinkEntity;
import edu.java.entity.UserEntity;
import edu.java.linkParser.GitHubRepositoryLinkParser;
import edu.java.linkParser.StackOverflowQuestionLinkParser;
import edu.java.repository.JpaLinkRepository;
import edu.java.repository.JpaUserRepository;
import edu.java.service.LinkService;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class JpaLinkService implements LinkService {
    private final JpaLinkRepository jpaLinkRepository;
    private final JpaUserRepository jpaUserRepository;

    @Autowired
    private StackOverflowQuestionClient stackOverflowQuestionClient;

    @Autowired
    private GitHubRepositoriesClient gitHubRepositoriesClient;

    public JpaLinkService(JpaLinkRepository linkRepository, JpaUserRepository userRepository) {
        this.jpaLinkRepository = linkRepository;
        this.jpaUserRepository = userRepository;
    }

    @Override
    @Transactional
    public void add(long tgChatId, String url) {
        if (!this.jpaLinkRepository.existsByUrl(url)) {
            LinkEntity linkEntity = new LinkEntity();
            int siteId;
            linkEntity.setUrl(url);
            linkEntity.setUpdatedAt(OffsetDateTime.now());
            linkEntity.setAddedAt(OffsetDateTime.now());
            if (url.startsWith("https://stackoverflow.com/")) {
                siteId = 1;
                StackOverflowQuestionResponse response = this.stackOverflowQuestionClient
                    .fetch(StackOverflowQuestionLinkParser.createRequest(url))
                    .block();
                linkEntity.setLastActivity(Objects.requireNonNull(response).items().getFirst().lastActivityDate());
                linkEntity.setSiteId(siteId);
                this.jpaLinkRepository.save(linkEntity);
            } else if (url.startsWith("https://github.com/")) {
                siteId = 2;
                GitHubRepositoryResponse response = this.gitHubRepositoriesClient
                    .fetch(GitHubRepositoryLinkParser.createRequest(url))
                    .block();
                linkEntity.setLastActivity(Objects.requireNonNull(response).updatedAt());
                linkEntity.setSiteId(siteId);
                this.jpaLinkRepository.save(linkEntity);
            } else {
                siteId = 0;
                linkEntity.setLastActivity(OffsetDateTime.now());
                linkEntity.setSiteId(siteId);
                this.jpaLinkRepository.save(linkEntity);
            }
        }
        LinkEntity linkEntity = this.jpaLinkRepository.getByUrl(url);
        UserEntity userEntity = this.jpaUserRepository.getReferenceById(tgChatId);
        linkEntity.addUser(userEntity);
    }

    @Override
    @Transactional
    public void remove(long tgChatId, String url) {
        if (this.jpaLinkRepository.existsByUrl(url)) {
            LinkEntity linkEntity = this.jpaLinkRepository.getByUrl(url);
            UserEntity userEntity = this.jpaUserRepository.getReferenceById(tgChatId);
            linkEntity.removeUser(userEntity);
        }
    }

    @Override
    @Transactional
    public Collection<LinkDTO> listAll(long tgChatId) {
        UserEntity userEntity = this.jpaUserRepository.getReferenceById(tgChatId);
        List<LinkDTO> list = new ArrayList<>();
        userEntity.getLinks().forEach(linkEntity -> list.add(new LinkDTO(
            linkEntity.getId(),
            linkEntity.getUrl(),
            linkEntity.getAddedAt(),
            linkEntity.getUpdatedAt(),
            linkEntity.getLastActivity(),
            linkEntity.getSiteId()
        )));

        return list;
    }
}
