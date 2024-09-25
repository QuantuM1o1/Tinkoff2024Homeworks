package edu.java.repository.jdbc;

import edu.java.dto.UserDTO;
import edu.java.repository.UsersRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUsersRepository implements UsersRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUsersRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addUser(long chatId) {
        String sql = "INSERT INTO users (tg_chat_id) VALUES (?)";

        this.jdbcTemplate.update(sql, chatId);
    }

    @Override
    public void removeUser(long chatId) {
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
