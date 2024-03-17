package edu.java.dao;

import edu.java.dto.LinkDTO;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcLinkDAO implements LinkDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcLinkDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void addLink(String url, OffsetDateTime lastActivity, int siteId) {
        String sql = "INSERT INTO links (url, added_at, updated_at, last_activity, site_id) VALUES (?, ?, ?, ?, ?)";

        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);
        Timestamp lastActivityStamp = Timestamp.from(lastActivity.toInstant());

        jdbcTemplate.update(sql, url, timestamp, timestamp, lastActivityStamp, siteId);
    }

    @Override
    @Transactional
    public void removeLink(String url) {
        String sql = "UPDATE links SET deleted_at = ? WHERE url = ?";

        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);

        jdbcTemplate.update(sql, timestamp, url);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LinkDTO> findAllLinks() {
        String sql = "SELECT * FROM links WHERE deleted_at IS NULL";

        return jdbcTemplate.query(sql, new DataClassRowMapper<>(LinkDTO.class));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LinkDTO> findLinkByUrl(String url) {
        String sql = "SELECT * FROM links WHERE deleted_at IS NULL AND url = ?";

        return jdbcTemplate.query(sql, new DataClassRowMapper<>(LinkDTO.class), url);
    }

    @Override
    public List<LinkDTO> findNLinksLastUpdated(int n) {
        String sql = "SELECT * FROM links WHERE deleted_at IS NULL ORDER BY updated_at ASC LIMIT ?";

        return jdbcTemplate.query(sql, new DataClassRowMapper<>(LinkDTO.class), n);
    }
}
