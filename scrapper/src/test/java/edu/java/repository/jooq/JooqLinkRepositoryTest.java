package edu.java.repository.jooq;

import edu.java.dto.LinkDTO;
import edu.java.scrapper.IntegrationTest;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
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
public class JooqLinkRepositoryTest extends IntegrationTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JooqLinksRepository linkRepository;

    private String url;

    private int siteId;

    private OffsetDateTime lastActivity;

    @BeforeEach
    void setUp() {
        this.url = "https://www.google.com/";
        this.siteId = 1;
        this.lastActivity = OffsetDateTime.now();
    }

    @Test
    @DisplayName("Добавление ссылки в таблицу")
    void addTest() {
        // given
        String sql = "SELECT * FROM links l INNER JOIN links_sites ls ON l.site_id = ls.id";

        // when
        this.linkRepository.addLink(this.url, this.lastActivity, this.siteId, 0, 0);
        List<LinkDTO> answer = this.jdbcTemplate.query(sql, new DataClassRowMapper<>(LinkDTO.class));

        // then
        assertThat(answer.size()).isEqualTo(1);
        assertThat(answer.getFirst().url()).isEqualTo(this.url);
    }

    @Test
    @DisplayName("Удаление ссылки из таблицы")
    void removeTest() {
        // given
        String url2 = "https://guessthe.game/";
        String sql = "SELECT * FROM links l INNER JOIN links_sites ls ON l.site_id = ls.id WHERE deleted_at IS NULL";
        String sqlDeleted
            = "SELECT * FROM links l INNER JOIN links_sites ls ON l.site_id = ls.id WHERE deleted_at IS NOT NULL";
        String sqlAdd = "INSERT INTO links (url, added_at, updated_at, last_activity, site_id) VALUES (?, ?, ?, ?, ?)";

        // when
        this.jdbcTemplate
            .update(sqlAdd, this.url, this.lastActivity, this.lastActivity, this.lastActivity, this.siteId);
        this.jdbcTemplate.update(sqlAdd, url2, this.lastActivity, this.lastActivity, this.lastActivity, this.siteId);
        this.linkRepository.removeLink(this.url);
        List<LinkDTO> answer = this.jdbcTemplate.query(sql, new DataClassRowMapper<>(LinkDTO.class));
        List<LinkDTO> deleted = this.jdbcTemplate.query(sqlDeleted, new DataClassRowMapper<>(LinkDTO.class));

        // then
        assertThat(answer.size()).isEqualTo(1);
        assertThat(answer.getFirst().url()).isEqualTo(url2);
        assertThat(deleted.size()).isEqualTo(1);
        assertThat(deleted.getFirst().url()).isEqualTo(this.url);
    }

    @Test
    @DisplayName("Чтение из таблицы")
    void findAllTest() {
        // given
        String url2 = "https://guessthe.game/";
        String sqlAdd = "INSERT INTO links (url, added_at, updated_at, last_activity, site_id) VALUES (?, ?, ?, ?, ?)";

        // when
        this.jdbcTemplate
            .update(sqlAdd, this.url, this.lastActivity, this.lastActivity, this.lastActivity, this.siteId);
        this.jdbcTemplate.update(sqlAdd, url2, this.lastActivity, this.lastActivity, this.lastActivity, this.siteId);
        List<LinkDTO> answer = this.linkRepository.findAllLinks();

        // then
        assertThat(answer.size()).isEqualTo(2);
        assertThat(answer.getFirst().url()).isEqualTo(this.url);
        assertThat(answer.getLast().url()).isEqualTo(url2);
    }
}
