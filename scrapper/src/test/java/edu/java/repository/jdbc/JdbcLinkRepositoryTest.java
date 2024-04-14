package edu.java.repository.jdbc;

import edu.java.dto.LinkDTO;
import edu.java.scrapper.IntegrationTest;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class JdbcLinkRepositoryTest extends IntegrationTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcLinkRepository linkRepository;

    @Test
    @DisplayName("Добавление ссылки в таблицу")
    void addTest() {
        // given
        String url = "https://www.google.com/";
        OffsetDateTime lastActivity = OffsetDateTime.now();
        int siteId = 0;
        String sql = "SELECT * FROM links l INNER JOIN links_sites ls ON l.site_id = ls.id";

        // when
        this.linkRepository.addLink(url, lastActivity, siteId, 0, 0);
        List<LinkDTO> answer = this.jdbcTemplate.query(sql, new DataClassRowMapper<>(LinkDTO.class));

        // then
        assertThat(answer.size()).isEqualTo(1);
        assertThat(answer.getFirst().url()).isEqualTo(url);
    }

    @Test
    @DisplayName("Удаление ссылки из таблицы")
    void removeTest() {
        // given
        String url1 = "https://www.google.com/";
        String url2 = "https://guessthe.game/";
        OffsetDateTime lastActivity = OffsetDateTime.now();
        int siteId = 1;
        String sql = "SELECT * FROM links l INNER JOIN links_sites ls ON l.site_id = ls.id WHERE deleted_at IS NULL";
        String sqlDeleted = "SELECT * FROM links l INNER JOIN links_sites ls ON l.site_id = ls.id "
            + "WHERE deleted_at IS NOT NULL";
        String sqlAdd = "INSERT INTO links (url, added_at, updated_at, last_activity, site_id) VALUES (?, ?, ?, ?, ?)";

        // when
        this.jdbcTemplate.update(sqlAdd, url1, lastActivity, lastActivity, lastActivity, siteId);
        this.jdbcTemplate.update(sqlAdd, url2, lastActivity, lastActivity, lastActivity, siteId);
        this.linkRepository.removeLink(url1);
        List<LinkDTO> answer = this.jdbcTemplate.query(sql, new DataClassRowMapper<>(LinkDTO.class));
        List<LinkDTO> deleted = this.jdbcTemplate.query(sqlDeleted, new DataClassRowMapper<>(LinkDTO.class));

        // then
        assertThat(answer.size()).isEqualTo(1);
        assertThat(answer.getFirst().url()).isEqualTo(url2);
        assertThat(deleted.size()).isEqualTo(1);
        assertThat(deleted.getFirst().url()).isEqualTo(url1);
    }

    @Test
    @DisplayName("Чтение из таблицы")
    void findAllTest() {
        // given
        String url1 = "https://www.google.com/";
        String url2 = "https://guessthe.game/";
        OffsetDateTime lastActivity = OffsetDateTime.now();
        int siteId = 1;
        String sqlAdd = "INSERT INTO links (url, added_at, updated_at, last_activity, site_id) VALUES (?, ?, ?, ?, ?)";

        // when
        this.jdbcTemplate.update(sqlAdd, url1, lastActivity, lastActivity, lastActivity, siteId);
        this.jdbcTemplate.update(sqlAdd, url2, lastActivity, lastActivity, lastActivity, siteId);
        List<LinkDTO> answer = this.linkRepository.findAllLinks();

        // then
        assertThat(answer.size()).isEqualTo(2);
    }
}
