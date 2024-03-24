package edu.java.dao;

import edu.java.dto.LinkDTO;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcUserLinkDAO implements UserLinkDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserLinkDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void addUserLink(Long chatId, Long linkId) {
        String sql = "INSERT INTO users_links (user_id, link_id) VALUES (?, ?)";

        this.jdbcTemplate.update(sql, chatId, linkId);
    }

    @Override
    @Transactional
    public void removeUserLink(Long chatId, Long linkId) {
        String sql = "UPDATE users_links SET deleted_at = ? WHERE user_id = ? AND link_id = ?";

        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);

        this.jdbcTemplate.update(sql, timestamp, chatId, linkId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LinkDTO> findAllLinksByUser(Long chatId) {
        String sql = "SELECT l.* FROM users_links ul "
            + "INNER JOIN links l ON ul.link_id = l.link_id "
            + "WHERE ul.user_id = ? AND ul.deleted_at IS NULL";

        return this.jdbcTemplate.query(sql, new DataClassRowMapper<>(LinkDTO.class), chatId);
    }
}
