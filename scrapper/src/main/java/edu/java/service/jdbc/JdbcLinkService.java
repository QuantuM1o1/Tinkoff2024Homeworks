package edu.java.service.jdbc;

import edu.java.clients.GitHubRepositoriesClient;
import edu.java.clients.StackOverflowQuestionClient;
import edu.java.dao.JdbcLinkDAO;
import edu.java.dao.JdbcUserLinkDAO;
import edu.java.dto.GitHubRepositoryResponse;
import edu.java.dto.LinkDTO;
import edu.java.dto.StackOverflowQuestionResponse;
import edu.java.linkParser.GitHubRepositoryLinkParser;
import edu.java.linkParser.StackOverflowQuestionLinkParser;
import edu.java.service.LinkService;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdbcLinkService implements LinkService {
    @Autowired
    private JdbcLinkDAO linkRepository;
    @Autowired
    private JdbcUserLinkDAO userLinkRepository;

    @Autowired
    GitHubRepositoriesClient gitHubRepositoriesClient;
    @Autowired
    StackOverflowQuestionClient stackOverflowQuestionClient;

    @Override
    public void add(long tgChatId, String url) {
        if (linkRepository.findLinkByUrl(url).isEmpty()) {
            int siteId;
            if (url.startsWith("https://stackoverflow.com/")) {
                siteId = 1;
                StackOverflowQuestionResponse response = stackOverflowQuestionClient
                    .fetch(StackOverflowQuestionLinkParser.createRequest(url))
                    .block();
                linkRepository.addLink(
                    url,
                    Objects.requireNonNull(response).items().getFirst().lastActivityDate(),
                    siteId);
            } else if (url.startsWith("https://github.com/")) {
                siteId = 2;
                GitHubRepositoryResponse response = gitHubRepositoriesClient
                    .fetch(GitHubRepositoryLinkParser.createRequest(url))
                    .block();
                linkRepository.addLink(url, Objects.requireNonNull(response).updatedAt(), siteId);
            } else {
                siteId = 0;
                linkRepository.addLink(url, OffsetDateTime.now(), siteId);
            }
        }
        long linkId = linkRepository.findLinkByUrl(url).getFirst().linkId();
        userLinkRepository.addUserLink(tgChatId, linkId);
    }

    @Override
    public void remove(long tgChatId, String url) {
        if (!linkRepository.findLinkByUrl(url).isEmpty()) {
            long linkId = linkRepository.findLinkByUrl(url).getFirst().linkId();
            userLinkRepository.removeUserLink(tgChatId, linkId);
        }
    }

    @Override
    public Collection<LinkDTO> listAll(long tgChatId) {
        return userLinkRepository.findAllLinksByUser(tgChatId);
    }
}
