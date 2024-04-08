package edu.java.service;

import edu.java.apiException.LinkAlreadyExistsException;
import edu.java.configuration.ResourcesConfig;
import edu.java.dto.LinkDTO;
import edu.java.repository.jdbc.JdbcLinkRepository;
import edu.java.repository.jdbc.JdbcUserLinkRepository;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LinkService {
    @Autowired
    private JdbcLinkRepository linkRepository;

    @Autowired
    private JdbcUserLinkRepository userLinkRepository;

    @Autowired
    private Map<String, UpdateChecker> updateCheckerMap;

    @Autowired
    private ResourcesConfig resourcesConfig;

    @Transactional
    public void add(long tgChatId, String url, String domain) throws LinkAlreadyExistsException {
        if (this.linkRepository.findLinkByUrl(url).isEmpty()) {
            if (this.updateCheckerMap.containsKey(domain)) {
                OffsetDateTime lastActivity = this.updateCheckerMap.get(domain).getLastActivity(url, domain);
                this.linkRepository.addLink(
                    url,
                    lastActivity,
                    this.resourcesConfig.supportedResources().get(domain).id()
                );
            } else {
                this.linkRepository.addLink(url, OffsetDateTime.now(), 0);
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

    public Collection<LinkDTO> listAll(long tgChatId) {
        return this.userLinkRepository.findAllLinksByUser(tgChatId);
    }

    public Collection<Long> findAllUsersForLink(long linkId) {
        return this.userLinkRepository.findAllUsersByLink(linkId);
    }

    private boolean linkExists(long tgChatId, long linkId) {
        return !this.userLinkRepository.findUserLink(tgChatId, linkId).isEmpty();
    }
}
