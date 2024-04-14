package edu.java.service;

import dto.LinkResponse;
import dto.ListLinksResponse;
import edu.java.apiException.LinkAlreadyExistsException;
import edu.java.configuration.ResourcesConfig;
import edu.java.dto.LinkDTO;
import edu.java.repository.LinkRepository;
import edu.java.repository.UserLinkRepository;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class LinkService {
    private final LinkRepository linkRepository;

    private final UserLinkRepository userLinkRepository;

    @Autowired
    private Map<String, UpdateChecker> updateCheckerMap;

    @Autowired
    private ResourcesConfig resourcesConfig;

    public LinkService(LinkRepository linkRepository, UserLinkRepository userLinkRepository) {
        this.linkRepository = linkRepository;
        this.userLinkRepository = userLinkRepository;
    }

    @Transactional
    public void add(long tgChatId, String url, String domain) throws LinkAlreadyExistsException {
        if (this.linkRepository.findLinkByUrl(url).isEmpty()) {
            if (this.updateCheckerMap.containsKey(domain)) {
                OffsetDateTime lastActivity = this.updateCheckerMap.get(domain).getLastActivity(url, domain);
                int answerCount = this.updateCheckerMap.get(domain).getAnswerCount(url, domain);
                int commentCount = this.updateCheckerMap.get(domain).getCommentCount(url, domain);
                this.linkRepository.addLink(
                    url,
                    lastActivity,
                    this.resourcesConfig.supportedResources().get(domain).id(),
                    answerCount,
                    commentCount
                );
            } else {
                this.linkRepository.addLink(url, OffsetDateTime.now(), 0, 0, 0);
            }
        } else {
            if (this.linkExists(
                tgChatId,
                this.linkRepository.findLinkByUrl(url).getFirst().linkId()
            )) {
                throw new LinkAlreadyExistsException();
            }
        }
        long linkId = this.linkRepository.findLinkByUrl(url).getFirst().linkId();
        this.userLinkRepository.addUserLink(tgChatId, linkId);
    }

    @Transactional
    public void remove(long tgChatId, String url) {
        long linkId = this.linkRepository.findLinkByUrl(url).getFirst().linkId();
        this.userLinkRepository.removeUserLink(tgChatId, linkId);
    }

    public ListLinksResponse listAll(long tgChatId) {
        List<LinkDTO> list =  this.userLinkRepository.findAllLinksByUser(tgChatId);
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
        return this.userLinkRepository.findAllUsersByLink(linkId);
    }

    private boolean linkExists(long tgChatId, long linkId) {
        return !this.userLinkRepository.findUserLink(tgChatId, linkId).isEmpty();
    }
}
