package edu.java.repository.jdbc;

import edu.java.dto.LinkDTO;
import edu.java.scrapper.IntegrationTest;
import java.time.OffsetDateTime;
import java.util.List;
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
    private JdbcUserLinkRepository userLinkRepository;

    @Autowired
    private JdbcUserRepository userRepository;

    @Autowired
    private JdbcLinkRepository linkRepository;

    @Test
    @DisplayName("Добавление связи пользователь-ссылка в таблицу")
    void addTest() {
        // given
        long chatId = 123L;
        userRepository.addUser(chatId);

        String url = "https://www.google.com/";
        OffsetDateTime lastActivity = OffsetDateTime.now();
        int siteId = 1;
        linkRepository.addLink(url, lastActivity, siteId, 0, 0);
        Long linkId = linkRepository.findAllLinks().getFirst().linkId();

        // when
        userLinkRepository.addUserLink(chatId, linkId);
        List<LinkDTO> answer = userLinkRepository.findAllLinksByUser(chatId);

        // then
        assertThat(answer.size()).isEqualTo(1);
        assertThat(answer.getFirst().linkId()).isEqualTo(linkId);
        assertThat(answer.getFirst().url()).isEqualTo(url);
    }

    @Test
    @DisplayName("Удаление одной из связей из таблицы")
    void removeTest() {
        // given
        long chatId = 123L;
        userRepository.addUser(chatId);

        String url1 = "https://www.google.com/";
        OffsetDateTime lastActivity = OffsetDateTime.now();
        int siteId = 1;

        linkRepository.addLink(url1, lastActivity, siteId, 0, 0);
        long linkId1 = linkRepository.findLinkByUrl(url1).getFirst().linkId();

        String url2 = "https://guessthe.game/";
        linkRepository.addLink(url2, lastActivity, siteId,0, 0);
        long linkId2 = linkRepository.findLinkByUrl(url2).getFirst().linkId();

        // when
        userLinkRepository.addUserLink(chatId, linkId1);
        userLinkRepository.addUserLink(chatId, linkId2);
        userLinkRepository.removeUserLink(chatId, linkId1);
        List<LinkDTO> answer = userLinkRepository.findAllLinksByUser(chatId);

        // then
        assertThat(answer.size()).isEqualTo(1);
        assertThat(answer.getFirst().url()).isEqualTo(url2);
    }

    @Test
    @DisplayName("Чтение из таблицы")
    void findAllTest() {
        // given
        long chatId = 123L;
        userRepository.addUser(chatId);

        String url1 = "https://www.google.com/";
        OffsetDateTime lastActivity = OffsetDateTime.now();
        int siteId = 1;

        linkRepository.addLink(url1, lastActivity, siteId, 0, 0);
        long linkId1 = linkRepository.findAllLinks().getLast().linkId();

        String url2 = "https://guessthe.game/";
        linkRepository.addLink(url2, lastActivity, siteId, 0,0);
        long linkId2 = linkRepository.findAllLinks().getLast().linkId();


        // when
        userLinkRepository.addUserLink(chatId, linkId1);
        userLinkRepository.addUserLink(chatId, linkId2);
        List<LinkDTO> answer = userLinkRepository.findAllLinksByUser(chatId);

        // then
        assertThat(answer.size()).isEqualTo(2);
    }
}
