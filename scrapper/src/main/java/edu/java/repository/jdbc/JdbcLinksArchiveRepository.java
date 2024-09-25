package edu.java.repository.jdbc;

import edu.java.repository.LinksArchiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
public class JdbcLinksArchiveRepository implements LinksArchiveRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcLinksArchiveRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLink(long linkId) {
        String sql = "INSERT INTO links_archive (link_id, added_at) VALUES (?, ?)";

        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);

        jdbcTemplate.update(sql, linkId, timestamp);
    }

    @Override
    public void removeLink(long linkId) {
        String sql = "UPDATE links SET deleted_at = ? WHERE link_id = ? AND deleted_at = NULL";

        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);

        this.jdbcTemplate.update(sql, timestamp, linkId);
    }
}
