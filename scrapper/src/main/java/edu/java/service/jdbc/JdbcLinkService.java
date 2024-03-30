package edu.java.service.jdbc;

import edu.java.client.GitHubRepositoriesClient;
import edu.java.client.StackOverflowQuestionClient;
import edu.java.dao.JdbcLinkDAO;
import edu.java.dao.JdbcUserLinkDAO;
import edu.java.dto.GitHubRepositoryResponse;
import edu.java.dto.LinkDTO;
import edu.java.dto.StackOverflowQuestionResponse;
import edu.java.linkParser.GitHubRepositoryLinkParser;
import edu.java.linkParser.StackOverflowQuestionLinkParser;
import edu.java.service.LinkService;
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
        if (this.linkRepository.findLinkByUrl(url).isEmpty()) {
            int siteId;
            if (url.startsWith("https://stackoverflow.com/")) {
                siteId = 1;
                StackOverflowQuestionResponse response = stackOverflowQuestionClient
                    .fetch(StackOverflowQuestionLinkParser.createRequest(url))
                    .block();
                this.linkRepository.addLink(
                    url,
                    Objects.requireNonNull(response).items().getFirst().lastActivityDate(),
                    siteId);
            } else if (url.startsWith("https://github.com/")) {
                siteId = 2;
                GitHubRepositoryResponse response = gitHubRepositoriesClient
                    .fetch(GitHubRepositoryLinkParser.createRequest(url))
                    .block();
                this.linkRepository.addLink(url, Objects.requireNonNull(response).updatedAt(), siteId);
            }
        }
        long linkId = this.linkRepository.findLinkByUrl(url).getFirst().linkId();
        this.userLinkRepository.addUserLink(tgChatId, linkId);
    }

    @Override
    public void remove(long tgChatId, String url) {
        long linkId = linkRepository.findLinkByUrl(url).getFirst().linkId();
        this.userLinkRepository.removeUserLink(tgChatId, linkId);
    }

    @Override
    public Collection<LinkDTO> listAll(long tgChatId) {
        return this.userLinkRepository.findAllLinksByUser(tgChatId);
    }

    @Override
    public Collection<Long> findAllUsersForLink(long linkId) {
        return this.userLinkRepository.findAllUsersByLink(linkId);
    }
}
