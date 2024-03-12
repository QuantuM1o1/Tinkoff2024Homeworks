package edu.java.dao;

import edu.java.dto.LinkDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcLinkDAO implements LinkDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcLinkDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void addLink(String url) {
        String sql = "INSERT INTO links (url, added_at, updated_at) VALUES (?, ?, ?)";

        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);

        jdbcTemplate.update(sql, url, timestamp, timestamp);
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

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(LinkDTO.class));
    }
}
