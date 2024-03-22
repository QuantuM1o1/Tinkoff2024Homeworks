package edu.java.dao;

import edu.java.dto.UserDTO;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcUserDAO implements UserDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void addUser(long chatId) {
        String sql = "INSERT INTO users (chat_id, added_at) VALUES (?, ?)";

        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);

        jdbcTemplate.update(sql, chatId, timestamp);
    }

    @Override
    @Transactional
        public void removeUser(long chatId) {
        String sql = "UPDATE users SET deleted_at = ? WHERE chat_id = ?";

        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);

        jdbcTemplate.update(sql, timestamp, chatId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAllUsers() {
        String sql = "SELECT * FROM users WHERE deleted_at IS NULL";

        return jdbcTemplate.query(sql, new DataClassRowMapper<>(UserDTO.class));
    }

    @Override
    public List<UserDTO> findUserById(long chatId) {
        String sql = "SELECT * FROM users WHERE deleted_at IS NULL AND chat_id = ?";

        return jdbcTemplate.query(sql, new DataClassRowMapper<>(UserDTO.class), chatId);
    }
}
