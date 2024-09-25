package edu.java.repository.jdbc;

import edu.java.dto.LinkDTO;
import edu.java.repository.UsersLinksRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUsersLinksRepository implements UsersLinksRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUsersLinksRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addUserLink(long chatId, long linkId) {
        String sql = "INSERT INTO users_links (user_id, link_id) VALUES (?, ?)";

        jdbcTemplate.update(sql, chatId, linkId);
    }

    @Override
    public void removeUserLink(long chatId, long linkId) {
        String sql = "DELETE FROM users_links WHERE user_id = ? AND link_id = ?";

        jdbcTemplate.update(sql, chatId, linkId);
    }

    @Override
    public List<LinkDTO> findAllLinksByUser(long chatId) {
        String sql = "SELECT * FROM users_links ul INNER JOIN links l ON ul.link_id = l.id "
            + "INNER JOIN links_sites ls ON l.site_id = ls.id WHERE ul.user_id = ?";

        return jdbcTemplate.query(sql, new DataClassRowMapper<>(LinkDTO.class), chatId);
    }

    @Override
    public List<Long> findAllUsersByLink(long linkId) {
        String sql = "SELECT user_id FROM users_links WHERE link_id = ?";

        return jdbcTemplate.queryForList(sql, Long.class, linkId);
    }

    @Override
    public List<Long> findUserLink(long chatId, long linkId) {
        String sql = "SELECT * FROM users_links WHERE user_id = ? AND link_id = ?";

        return jdbcTemplate.queryForList(sql, Long.class, chatId, linkId);
    }
}
