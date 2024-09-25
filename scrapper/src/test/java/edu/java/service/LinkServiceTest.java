package edu.java.service;

import dto.ListLinksResponse;
import edu.java.apiException.LinkAlreadyExistsException;
import edu.java.dto.LinkDTO;
import edu.java.repository.jdbc.JdbcLinksRepository;
import edu.java.repository.jdbc.JdbcUsersLinksRepository;
import edu.java.repository.jdbc.JdbcUsersRepository;
import edu.java.scrapper.IntegrationTest;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class LinkServiceTest extends IntegrationTest {
    @Autowired
    private LinkService linkService;

    @Autowired
    private JdbcUsersLinksRepository userLinkRepository;

    @Autowired
    private JdbcUsersRepository userRepository;

    @Autowired
    private JdbcLinksRepository linkRepository;

    private long id;

    private String url;

    private String domain;

    @BeforeEach
    void setUp() {
        this.id = 123L;
        this.url = "https://stackoverflow.com/questions/31322043/project-euler-8-answer-fails-to-be-true";
        this.domain = "stackoverflow.com";
        int siteId = 1;
        int answers = 2;
        int comments = 3;
        OffsetDateTime time = OffsetDateTime.now();
        this.userRepository.addUser(this.id);
        this.linkRepository.addLink(this.url, time, siteId, answers, comments);
    }

    @Test
    @DisplayName("Добавление ссылки")
    public void add() throws LinkAlreadyExistsException {
        // given

        // when
        this.linkService.add(this.id, this.url, this.domain);
        List<LinkDTO> answer = this.userLinkRepository.findAllLinksByUser(this.id);

        // then
        assertThat(answer.size()).isEqualTo(1);
        assertThat(answer.getFirst().url()).isEqualTo(this.url);
        assertThat(answer.getFirst().domainName()).isEqualTo(this.domain);
    }

    @Test
    @DisplayName("Удаление ссылки")
    public void remove() throws LinkAlreadyExistsException {
        // given
        this.linkService.add(this.id, this.url, this.domain);

        // when
        List<LinkDTO> answer = this.userLinkRepository.findAllLinksByUser(this.id);
        assertThat(answer.size()).isEqualTo(1);

        this.linkService.remove(this.id, this.url);
        answer = this.userLinkRepository.findAllLinksByUser(this.id);

        // then
        assertThat(answer.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Вывод всех ссылок по пользователю")
    public void returnAllLinksForUser() throws LinkAlreadyExistsException {
        // given
        this.linkService.add(this.id, this.url, this.domain);

        // when
        ListLinksResponse answer = this.linkService.listAll(this.id);

        // then
        assertThat(answer.size()).isEqualTo(1);
        assertThat(answer.links().getFirst().url()).isEqualTo(URI.create(this.url));
    }

    @Test
    @DisplayName("Вывод всех пользователей по ссылке")
    public void returnAllUsersForLink() {
        // given
        List<LinkDTO> list = this.linkRepository.findLinkByUrl(this.url);
        this.userLinkRepository.addUserLink(this.id, list.getFirst().id());

        // when
        List<Long> answer = (List<Long>) this.linkService.findAllUsersForLink(list.getFirst().id());

        // then
        assertThat(answer.size()).isEqualTo(1);
        assertThat(answer.getFirst()).isEqualTo(this.id);
    }
}
