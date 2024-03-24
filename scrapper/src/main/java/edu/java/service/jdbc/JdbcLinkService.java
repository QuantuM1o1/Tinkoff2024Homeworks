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
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;

public class JdbcLinkService implements LinkService {
    private final JdbcLinkDAO jdbcLinkRepository;
    private final JdbcUserLinkDAO jdbcUserLinkRepository;

    @Autowired
    private GitHubRepositoriesClient gitHubRepositoriesClient;

    @Autowired
    private StackOverflowQuestionClient stackOverflowQuestionClient;

    public JdbcLinkService(JdbcLinkDAO jdbcLinkRepository, JdbcUserLinkDAO jdbcUserLinkRepository) {
        this.jdbcLinkRepository = jdbcLinkRepository;
        this.jdbcUserLinkRepository = jdbcUserLinkRepository;
    }

    @Override
    public void add(long tgChatId, String url) {
        if (jdbcLinkRepository.findLinkByUrl(url).isEmpty()) {
            int siteId;
            if (url.startsWith("https://stackoverflow.com/")) {
                siteId = 1;
                StackOverflowQuestionResponse response = stackOverflowQuestionClient
                    .fetch(StackOverflowQuestionLinkParser.createRequest(url))
                    .block();
                jdbcLinkRepository.addLink(
                    url,
                    Objects.requireNonNull(response).items().getFirst().lastActivityDate(),
                    siteId,
                    Objects.requireNonNull(response).items().getFirst().answerCount(),
                    Objects.requireNonNull(response).items().getFirst().commentCount());
            } else if (url.startsWith("https://github.com/")) {
                siteId = 2;
                GitHubRepositoryResponse response = gitHubRepositoriesClient
                    .fetch(GitHubRepositoryLinkParser.createRequest(url))
                    .block();
                jdbcLinkRepository.addLink(url, Objects.requireNonNull(response).updatedAt(), siteId, 0, 0);
            } else {
                siteId = 0;
                jdbcLinkRepository.addLink(url, OffsetDateTime.now(), siteId, 0, 0);
            }
        }
        long linkId = jdbcLinkRepository.findLinkByUrl(url).getFirst().linkId();
        jdbcUserLinkRepository.addUserLink(tgChatId, linkId);
    }

    @Override
    public void remove(long tgChatId, String url) {
        if (!jdbcLinkRepository.findLinkByUrl(url).isEmpty()) {
            long linkId = jdbcLinkRepository.findLinkByUrl(url).getFirst().linkId();
            jdbcUserLinkRepository.removeUserLink(tgChatId, linkId);
        }
    }

    @Override
    public Collection<LinkDTO> listAll(long tgChatId) {
        return jdbcUserLinkRepository.findAllLinksByUser(tgChatId);
    }
}
