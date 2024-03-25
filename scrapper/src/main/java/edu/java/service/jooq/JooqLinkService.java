package edu.java.service.jooq;

import edu.java.client.GitHubRepositoriesClient;
import edu.java.client.StackOverflowQuestionClient;
import edu.java.dao.jooq.JooqLinkDAO;
import edu.java.dao.jooq.JooqUserLinkDAO;
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

public class JooqLinkService implements LinkService {
    private final JooqLinkDAO jooqLinkRepository;
    private final JooqUserLinkDAO jooqUserLinkRepository;

    @Autowired
    private GitHubRepositoriesClient gitHubRepositoriesClient;

    @Autowired
    private StackOverflowQuestionClient stackOverflowQuestionClient;

    public JooqLinkService(JooqLinkDAO jooqLinkRepository, JooqUserLinkDAO jooqUserLinkRepository) {
        this.jooqLinkRepository = jooqLinkRepository;
        this.jooqUserLinkRepository = jooqUserLinkRepository;
    }

    @Override
    public void add(long tgChatId, String url) {
        if (this.jooqLinkRepository.findLinkByUrl(url).isEmpty()) {
            int siteId;
            if (url.startsWith("https://stackoverflow.com/")) {
                siteId = 1;
                StackOverflowQuestionResponse response = this.stackOverflowQuestionClient
                    .fetch(StackOverflowQuestionLinkParser.createRequest(url))
                    .block();
                this.jooqLinkRepository.addLink(
                    url,
                    Objects.requireNonNull(response).items().getFirst().lastActivityDate(),
                    siteId,
                    Objects.requireNonNull(response).items().getFirst().answerCount(),
                    Objects.requireNonNull(response).items().getFirst().commentCount());
            } else if (url.startsWith("https://github.com/")) {
                siteId = 2;
                GitHubRepositoryResponse response = this.gitHubRepositoriesClient
                    .fetch(GitHubRepositoryLinkParser.createRequest(url))
                    .block();
                this.jooqLinkRepository
                    .addLink(url, Objects.requireNonNull(response).updatedAt(), siteId, 0, 0);
            } else {
                siteId = 0;
                this.jooqLinkRepository.addLink(url, OffsetDateTime.now(), siteId, 0, 0);
            }
        }
        long linkId = this.jooqLinkRepository.findLinkByUrl(url).getFirst().linkId();
        this.jooqUserLinkRepository.addUserLink(tgChatId, linkId);
    }

    @Override
    public void remove(long tgChatId, String url) {
        if (!this.jooqLinkRepository.findLinkByUrl(url).isEmpty()) {
            long linkId = this.jooqLinkRepository.findLinkByUrl(url).getFirst().linkId();
            this.jooqUserLinkRepository.removeUserLink(tgChatId, linkId);
        }
    }

    @Override
    public Collection<LinkDTO> listAll(long tgChatId) {
        return this.jooqUserLinkRepository.findAllLinksByUser(tgChatId);
    }
}
