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
public class JdbcUserLinkDAO implements UserLinkDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserLinkDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void addUserLink(Long chatId, Long linkId) {
        String sql = "INSERT INTO user_links (user_id, link_id) VALUES (?, ?)";

        jdbcTemplate.update(sql, chatId, linkId);
    }

    @Override
    @Transactional
    public void removeUserLink(Long chatId, Long linkId) {
        String sql = "UPDATE user_links SET deleted_at = ? WHERE user_id = ? AND link_id = ?";

        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);

        jdbcTemplate.update(sql, timestamp, chatId, linkId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LinkDTO> findAllLinksByUser(Long chatId) {
        String sql = "SELECT l.* FROM user_links ul " +
            "INNER JOIN links l ON ul.link_id = l.link_id " +
            "WHERE ul.user_id = ? AND ul.deleted_at IS NULL";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(LinkDTO.class), chatId);
    }
}
