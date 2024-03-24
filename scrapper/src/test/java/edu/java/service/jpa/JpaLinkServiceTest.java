package edu.java.service.jpa;

import edu.java.dto.LinkDTO;
import edu.java.repository.JpaLinkRepository;
import edu.java.repository.JpaUserRepository;
import edu.java.scrapper.IntegrationTest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class JpaLinkServiceTest extends IntegrationTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JpaLinkRepository jpaLinkRepository;

    @Autowired
    private JpaUserRepository jpaUserRepository;

    private JpaLinkService jpaLinkService;

    @BeforeEach
    void setUp() {
        this.jpaLinkService = new JpaLinkService(this.jpaLinkRepository, this.jpaUserRepository);
    }

    @Test
    @DisplayName("Добавление ссылки")
    @Transactional
    @Rollback
    void addTest() {
        // given
        String url = "https://www.google.com/";
        int siteId = 0;
        String selectLinks = "SELECT * FROM links";
        String addUser = "INSERT INTO users (chat_id, added_at) VALUES (?, ?)";
        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);
        long tgChatId = 123L;

        // when
        this.jdbcTemplate.update(addUser, tgChatId, timestamp);
        this.jpaLinkService.add(tgChatId, url);
        List<LinkDTO> answer = this.jdbcTemplate.query(selectLinks, new DataClassRowMapper<>(LinkDTO.class));

        // then
        assertThat(answer.size()).isEqualTo(1);
        assertThat(answer.getFirst().url()).isEqualTo(url);
        assertThat(answer.getFirst().siteId()).isEqualTo(siteId);
    }

    @Test
    @DisplayName("Удаление ссылки")
    @Transactional
    @Rollback
    void removeTest() {
        // given
        String url = "https://www.google.com/";
        String addUser = "INSERT INTO users (chat_id, added_at) VALUES (?, ?)";
        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);
        long tgChatId = 123L;

        // when
        this.jdbcTemplate.update(addUser, tgChatId, timestamp);
        this.jpaLinkService.add(tgChatId, url);

        List<LinkDTO> answer = (List<LinkDTO>) this.jpaLinkService.listAll(tgChatId);
        assertThat(answer.size()).isEqualTo(1);

        this.jpaLinkService.remove(tgChatId, url);
        answer = (List<LinkDTO>) this.jpaLinkService.listAll(tgChatId);

        // then
        assertThat(answer.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Все ссылки")
    @Transactional
    @Rollback
    void listTest() {
        // given
        String url1 = "https://www.google.com/";
        String url2 = "https://guessthe.game/";
        int siteId = 0;
        String addUser = "INSERT INTO users (chat_id, added_at) VALUES (?, ?)";
        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);
        long tgChatId = 123L;

        // when
        this.jdbcTemplate.update(addUser, tgChatId, timestamp);
        this.jpaLinkService.add(tgChatId, url1);
        this.jpaLinkService.add(tgChatId, url2);
        List<LinkDTO> answer = (List<LinkDTO>) this.jpaLinkService.listAll(tgChatId);

        // then
        assertThat(answer.size()).isEqualTo(2);
        assertThat(answer.getFirst().url()).isEqualTo(url1);
        assertThat(answer.getLast().url()).isEqualTo(url2);
        assertThat(answer.getFirst().siteId()).isEqualTo(siteId);
    }
}
