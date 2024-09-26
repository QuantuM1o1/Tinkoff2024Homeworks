package edu.java.repository.jdbc;

import edu.java.repository.UsersArchiveRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUsersArchiveRepository implements UsersArchiveRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUsersArchiveRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addUser(long chatId) {
        String sql = "INSERT INTO users_archive (tg_chat_id, added_at) VALUES (?, ?)";

        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);

        this.jdbcTemplate.update(sql, chatId, timestamp);
    }

    @Override
    public void removeUser(long chatId) {
        String sql = "UPDATE users_archive SET deleted_at = ? WHERE tg_chat_id = ? AND deleted_at = NULL";

        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);

        this.jdbcTemplate.update(sql, timestamp, chatId);
    }
}
