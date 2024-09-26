package edu.java.repository.jdbc;

import edu.java.repository.UsersLinksArchiveRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUsersLinksArchiveRepository implements UsersLinksArchiveRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUsersLinksArchiveRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addUserLink(long chatId, String url) {
        String sql = "INSERT INTO users_links_archive (user_id, url, added_at) VALUES (?, ?, ?)";

        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);

        this.jdbcTemplate.update(sql, chatId, url, timestamp);
    }

    @Override
    public void removeUserLink(long chatId, String url) {
        String sql = "UPDATE users_links_archive SET deleted_at = ? "
            + "WHERE user_id = ? AND url = ? AND deleted_at = NULL";

        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);

        this.jdbcTemplate.update(sql, timestamp, chatId, url);
    }
}
