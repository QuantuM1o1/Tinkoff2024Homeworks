package edu.java.repository.jdbc;

import edu.java.dto.LinkDTO;
import edu.java.scrapper.IntegrationTest;
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
public class JdbcUserLinkRepositoryTest extends IntegrationTest {
    @Autowired
    private JdbcUsersLinksRepository userLinkRepository;

    @Autowired
    private JdbcUsersRepository userRepository;

    @Autowired
    private JdbcLinksRepository linkRepository;

    private long chatId;

    private String url;

    private OffsetDateTime lastActivity;

    private int siteId;

    private Long linkId;

    @BeforeEach
    void setUp() {
        this.chatId = 123L;
        this.url = "https://www.google.com/";
        this.userRepository.addUser(this.chatId);
        this.lastActivity = OffsetDateTime.now();
        this.siteId = 1;
        this.linkRepository.addLink(this.url, this.lastActivity, this.siteId, 0, 0);
        this.linkId = this.linkRepository.findAllLinks().getFirst().id();
    }

    @Test
    @DisplayName("Добавление связи пользователь-ссылка в таблицу")
    void addTest() {
        // given

        // when
        this.userLinkRepository.addUserLink(this.chatId, this.linkId);
        List<LinkDTO> answer = this.userLinkRepository.findAllLinksByUser(this.chatId);

        // then
        assertThat(answer.size()).isEqualTo(1);
        assertThat(answer.getFirst().id()).isEqualTo(this.linkId);
        assertThat(answer.getFirst().url()).isEqualTo(this.url);
    }

    @Test
    @DisplayName("Удаление одной из связей из таблицы")
    void removeTest() {
        // given
        String url2 = "https://guessthe.game/";
        this.linkRepository.addLink(url2, this.lastActivity, this.siteId, 0, 0);
        long linkId2 = this.linkRepository.findLinkByUrl(url2).getFirst().id();

        // when
        this.userLinkRepository.addUserLink(this.chatId, this.linkId);
        this.userLinkRepository.addUserLink(this.chatId, linkId2);
        this.userLinkRepository.removeUserLink(this.chatId, this.linkId);
        List<LinkDTO> answer = this.userLinkRepository.findAllLinksByUser(this.chatId);

        // then
        assertThat(answer.size()).isEqualTo(1);
        assertThat(answer.getFirst().url()).isEqualTo(url2);
    }

    @Test
    @DisplayName("Чтение из таблицы")
    void findAllTest() {
        // given
        String url2 = "https://guessthe.game/";
        this.linkRepository.addLink(url2, this.lastActivity, this.siteId, 0, 0);
        long linkId2 = this.linkRepository.findAllLinks().getLast().id();

        // when
        this.userLinkRepository.addUserLink(this.chatId, this.linkId);
        this.userLinkRepository.addUserLink(this.chatId, linkId2);
        List<LinkDTO> answer = this.userLinkRepository.findAllLinksByUser(this.chatId);

        // then
        assertThat(answer.size()).isEqualTo(2);
    }
}
