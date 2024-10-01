package edu.java.repository.jdbc;

import edu.java.dto.UserDTO;
import edu.java.repository.UsersRepository;
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
        String sql = "DELETE FROM users WHERE tg_chat_id = ?";

        this.jdbcTemplate.update(sql, chatId);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        String sql = "SELECT * FROM users";

        return this.jdbcTemplate.query(sql, new DataClassRowMapper<>(UserDTO.class));
    }

    @Override
    public List<UserDTO> findUserById(long chatId) {
        String sql = "SELECT * FROM users WHERE tg_chat_id = ?";

        return this.jdbcTemplate.query(sql, new DataClassRowMapper<>(UserDTO.class), chatId);
    }
}
