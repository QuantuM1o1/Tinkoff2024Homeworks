package edu.java.service;

import dto.LinkResponse;
import dto.ListLinksResponse;
import edu.java.apiException.LinkAlreadyExistsException;
import edu.java.configuration.ResourcesConfig;
import edu.java.dto.LinkDTO;
import edu.java.dto.UpdateCheckerResponse;
import edu.java.repository.LinksArchiveRepository;
import edu.java.repository.LinksRepository;
import edu.java.repository.UsersLinksArchiveRepository;
import edu.java.repository.UsersLinksRepository;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class LinkService {
    private final LinksRepository linksRepository;

    private final LinksArchiveRepository linksArchiveRepository;

    private final UsersLinksRepository usersLinksRepository;

    private final UsersLinksArchiveRepository usersLinksArchiveRepository;

    @Autowired
    private Map<String, UpdateChecker> updateCheckerMap;

    @Autowired
    private ResourcesConfig resourcesConfig;

    public LinkService(
        LinksRepository linksRepository, LinksArchiveRepository linksArchiveRepository,
        UsersLinksRepository usersLinksRepository, UsersLinksArchiveRepository usersLinksArchiveRepository
    ) {
        this.linksRepository = linksRepository;
        this.linksArchiveRepository = linksArchiveRepository;
        this.usersLinksRepository = usersLinksRepository;
        this.usersLinksArchiveRepository = usersLinksArchiveRepository;
    }

    @Transactional
    public void add(long tgChatId, String url, String domain) throws LinkAlreadyExistsException {
        if (this.linksRepository.findLinkByUrl(url).isEmpty()) {
            if (this.updateCheckerMap.containsKey(domain)) {
                this.linksRepository.addLink(
                    url,
                    OffsetDateTime.now(),
                    this.resourcesConfig.supportedResources().get(domain).id(),
                    0,
                    0
                );
                LinkDTO link = this.linksRepository.findLinkByUrl(url).getFirst();
                UpdateCheckerResponse response = this.updateCheckerMap.get(domain).updateLink(link);
                this.linksRepository.setLastActivity(url, response.lastActivity());
                this.linksRepository.setAnswerCount(url, response.answerCount());
                this.linksRepository.setCommentCount(url, response.commentCount());
            } else {
                this.linksRepository.addLink(url, OffsetDateTime.now(), 0, 0, 0);
            }
        } else {
            if (this.linkExists(
                tgChatId,
                this.linksRepository.findLinkByUrl(url).getFirst().linkId()
            )) {
                throw new LinkAlreadyExistsException();
            }
        }
        long linkId = this.linksRepository.findLinkByUrl(url).getFirst().linkId();
        this.usersLinksRepository.addUserLink(tgChatId, linkId);
    }

    @Transactional
    public void remove(long tgChatId, String url) {
        long linkId = this.linksRepository.findLinkByUrl(url).getFirst().linkId();
        this.usersLinksRepository.removeUserLink(tgChatId, linkId);
    }

    public ListLinksResponse listAll(long tgChatId) {
        List<LinkDTO> list =  this.usersLinksRepository.findAllLinksByUser(tgChatId);
        List<LinkResponse> responseList = new ArrayList<>();
        for (LinkDTO link : list) {
            LinkResponse linkResponse = new LinkResponse(
                link.linkId(),
                URI.create(link.url())
            );
            responseList.add(linkResponse);
        }

        return new ListLinksResponse(
            responseList,
            responseList.size()
        );
    }

    public Collection<Long> findAllUsersForLink(long linkId) {
        return this.usersLinksRepository.findAllUsersByLink(linkId);
    }

    @Transactional
    public void changeUpdatedAtToNow(String url) {
        this.linksRepository.setUpdatedAt(url, OffsetDateTime.now());
    }

    @Transactional
    public void changeLastActivity(String url, OffsetDateTime lastActivity) {
        this.linksRepository.setLastActivity(url, lastActivity);
    }

    private boolean linkExists(long tgChatId, long linkId) {
        return !this.usersLinksRepository.findUserLink(tgChatId, linkId).isEmpty();
    }
}
