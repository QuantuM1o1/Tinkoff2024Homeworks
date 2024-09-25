package edu.java.repository.jdbc;

import edu.java.repository.UsersLinksArchiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
public class JdbcUsersLinksArchiveRepository implements UsersLinksArchiveRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUsersLinksArchiveRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addUserLink(long chatId, long linkId) {
        String sql = "INSERT INTO users_links_archive (user_id, link_id, added_at) VALUES (?, ?, ?)";

        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);

        this.jdbcTemplate.update(sql, chatId, linkId, timestamp);
    }

    @Override
    public void removeUserLink(long chatId, long linkId) {
        String sql = "UPDATE users_links_archive SET deleted_at = ? "
            + "WHERE user_id = ? AND link_id = ? AND deleted_at = NULL";

        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);

        this.jdbcTemplate.update(sql, timestamp, chatId, linkId);
    }
}
