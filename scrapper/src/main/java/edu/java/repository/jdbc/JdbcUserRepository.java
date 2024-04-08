package edu.java.repository.jdbc;

import edu.java.dto.UserDTO;
import edu.java.repository.UserRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addUser(Long chatId) {
        String sql = "INSERT INTO users (chat_id, added_at) VALUES (?, ?)";

        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);

        this.jdbcTemplate.update(sql, chatId, timestamp);
    }

    @Override
    public void removeUser(Long chatId) {
        String sql = "UPDATE users SET deleted_at = ? WHERE chat_id = ?";

        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);

        this.jdbcTemplate.update(sql, timestamp, chatId);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        String sql = "SELECT * FROM users WHERE deleted_at IS NULL";

        return this.jdbcTemplate.query(sql, new DataClassRowMapper<>(UserDTO.class));
    }

    @Override
    public List<UserDTO> findUserById(long chatId) {
        String sql = "SELECT * FROM users WHERE deleted_at IS NULL AND chat_id = ?";

        return this.jdbcTemplate.query(sql, new DataClassRowMapper<>(UserDTO.class), chatId);
    }
}
