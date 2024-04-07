package edu.java.repository.jdbc;

import edu.java.dto.LinkDTO;
import edu.java.repository.UserLinkRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserLinkRepository implements UserLinkRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserLinkRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addUserLink(long chatId, long linkId) {
        String sql = "INSERT INTO user_links (user_id, link_id) VALUES (?, ?)";

        jdbcTemplate.update(sql, chatId, linkId);
    }

    @Override
    public void removeUserLink(long chatId, long linkId) {
        String sql = "UPDATE user_links SET deleted_at = ? WHERE user_id = ? AND link_id = ?";

        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);

        jdbcTemplate.update(sql, timestamp, chatId, linkId);
    }

    @Override
    public List<LinkDTO> findAllLinksByUser(long chatId) {
        String sql = "SELECT * FROM user_links ul INNER JOIN links l ON ul.link_id = l.link_id "
            + "INNER JOIN links_sites ls ON l.site_id = ls.id WHERE ul.user_id = ? AND ul.deleted_at IS NULL";

        return jdbcTemplate.query(sql, new DataClassRowMapper<>(LinkDTO.class), chatId);
    }

    @Override
    public List<Long> findAllUsersByLink(long linkId) {
        String sql = "SELECT u.chat_id FROM user_links ul INNER JOIN users u ON ul.user_id = u.chat_id "
            + "WHERE ul.link_id = ? AND ul.deleted_at IS NULL";

        return jdbcTemplate.queryForList(sql, Long.class, linkId);
    }

    @Override
    public List<Long> findUserLink(long chatId, long linkId) {
        String sql = "SELECT * FROM user_links WHERE user_id = ? AND link_id = ? AND deleted_at IS NULL";

        return jdbcTemplate.queryForList(sql, Long.class, chatId, linkId);
    }
}
