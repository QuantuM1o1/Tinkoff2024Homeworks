package edu.java.service.jpa;

import edu.java.dto.LinkDTO;
import edu.java.repository.JpaLinkRepository;
import edu.java.scrapper.IntegrationTest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class JpaLinkUpdaterServiceTest extends IntegrationTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JpaLinkRepository linkRepository;

    private JpaLinkUpdaterService jpaLinkUpdaterService;

    @BeforeEach
    void setUp() {
        this.jpaLinkUpdaterService = new JpaLinkUpdaterService(this.linkRepository);
    }

    @Test
    @DisplayName("Поиск ссылок для обновления")
    @Transactional
    @Rollback
    void findTest() {
        // given
        String url1 = "https://www.google.com/";
        String url2 = "https://guessthe.game/";
        String url3 = "https://web.telegram.org/";
        String url4 = "https://github.com/";
        int siteId = 0;
        int n1 = 1;
        int n2 = 3;
        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);
        String sql = "INSERT INTO links (url, added_at, updated_at, last_activity, site_id) VALUES (?, ?, ?, ?, ?)";

        this.jdbcTemplate.update(sql, url1, timestamp, timestamp, timestamp, siteId);
        this.jdbcTemplate.update(sql, url2, timestamp, timestamp, timestamp, siteId);
        this.jdbcTemplate.update(sql, url3, timestamp, timestamp, timestamp, siteId);
        this.jdbcTemplate.update(sql, url4, timestamp, timestamp, timestamp, siteId);

        // when
        List<LinkDTO> answer1 = this.jpaLinkUpdaterService.findNLinksToUpdate(n1);
        List<LinkDTO> answer2 = this.jpaLinkUpdaterService.findNLinksToUpdate(n2);

        // then
        assertThat(answer1.size()).isEqualTo(n1);
        assertThat(answer1.getFirst().url()).isEqualTo(url1);
        assertThat(answer1.getFirst().siteId()).isEqualTo(siteId);
        assertThat(answer2.size()).isEqualTo(n2);
        assertThat(answer2.getFirst().siteId()).isEqualTo(siteId);
    }
}
