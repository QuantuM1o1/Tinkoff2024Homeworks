package edu.java.dao;

import edu.java.dto.LinkDTO;
import edu.java.scrapper.IntegrationTest;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class JdbcUserLinkDAOTest extends IntegrationTest {
    @Autowired
    private JdbcUserLinkDAO userLinkRepository;
    @Autowired
    private JdbcUserDAO userRepository;
    @Autowired
    private JdbcLinkDAO linkRepository;

    @Test
    @DisplayName("Добавление связи пользователь-ссылка в таблицу")
    @Transactional
    @Rollback
    void addTest() {
        // given
        long chatId = 123L;
        userRepository.addUser(chatId);

        String url = "https://www.google.com/";
        OffsetDateTime lastActivity = OffsetDateTime.now();
        int siteId = 1;
        linkRepository.addLink(url, lastActivity, siteId);
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
    @Transactional
    @Rollback
    void removeTest() {
        // given
        long chatId = 123L;
        userRepository.addUser(chatId);

        String url1 = "https://www.google.com/";
        OffsetDateTime lastActivity = OffsetDateTime.now();
        int siteId = 1;
        linkRepository.addLink(url1, lastActivity, siteId);
        Long linkId1 = linkRepository.findAllLinks().getLast().linkId();

        String url2 = "https://guessthe.game/";
        linkRepository.addLink(url2, lastActivity, siteId);
        Long linkId2 = linkRepository.findAllLinks().getLast().linkId();

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
    @Transactional
    @Rollback
    void findAllTest() {
        // given
        long chatId = 123L;
        userRepository.addUser(chatId);

        String url1 = "https://www.google.com/";
        OffsetDateTime lastActivity = OffsetDateTime.now();
        int siteId = 1;
        linkRepository.addLink(url1, lastActivity, siteId);
        Long linkId1 = linkRepository.findAllLinks().getLast().linkId();

        String url2 = "https://guessthe.game/";
        linkRepository.addLink(url2, lastActivity, siteId);
        Long linkId2 = linkRepository.findAllLinks().getLast().linkId();


        // when
        userLinkRepository.addUserLink(chatId, linkId1);
        userLinkRepository.addUserLink(chatId, linkId2);
        List<LinkDTO> answer = userLinkRepository.findAllLinksByUser(chatId);

        // then
        assertThat(answer.size()).isEqualTo(2);
        assertThat(answer.getFirst().url()).isEqualTo(url1);
        assertThat(answer.getLast().url()).isEqualTo(url2);
    }
}
