package edu.java.repository.jdbc;

import edu.java.dto.UserDTO;
import edu.java.scrapper.IntegrationTest;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class JdbcUserRepositoryTest extends IntegrationTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcUserRepository userRepository;

    private long chatId;

    @BeforeEach
    void setUp() {
        this.chatId = 123L;
    }

    @Test
    @DisplayName("Добавление пользователя в таблицу")
    void addTest() {
        // given
        String sql = "SELECT * FROM users";

        // when
        userRepository.addUser(this.chatId);
        List<UserDTO> answer = jdbcTemplate.query(sql, new DataClassRowMapper<>(UserDTO.class));

        // then
        assertThat(answer.size()).isEqualTo(1);
        assertThat(answer.getFirst().chatId()).isEqualTo(this.chatId);
    }

    @Test
    @DisplayName("Удаление пользователя из таблицы")
    void removeTest() {
        // given
        long chatId2 = 1231L;
        OffsetDateTime addedAt = OffsetDateTime.now();
        String sql = "SELECT * FROM users WHERE deleted_at IS NULL";
        String sqlDeleted = "SELECT * FROM users WHERE deleted_at IS NOT NULL";
        String sqlAdd = "INSERT INTO users (chat_id, added_at) VALUES (?, ?)";

        // when
        jdbcTemplate.update(sqlAdd, this.chatId, addedAt);
        jdbcTemplate.update(sqlAdd, chatId2, addedAt);
        userRepository.removeUser(this.chatId);
        List<UserDTO> answer = jdbcTemplate.query(sql, new DataClassRowMapper<>(UserDTO.class));
        List<UserDTO> deleted = jdbcTemplate.query(sqlDeleted, new DataClassRowMapper<>(UserDTO.class));

        // then
        assertThat(answer.size()).isEqualTo(1);
        assertThat(answer.getFirst().chatId()).isEqualTo(chatId2);
        assertThat(deleted.size()).isEqualTo(1);
        assertThat(deleted.getFirst().chatId()).isEqualTo(this.chatId);
    }

    @Test
    @DisplayName("Чтение из таблицы")
    void findAllTest() {
        // given
        long chatId2 = 1231L;
        OffsetDateTime addedAt = OffsetDateTime.now();
        String sqlAdd = "INSERT INTO users (chat_id, added_at) VALUES (?, ?)";

        // when
        jdbcTemplate.update(sqlAdd, this.chatId, addedAt);
        jdbcTemplate.update(sqlAdd, chatId2, addedAt);
        List<UserDTO> answer = userRepository.findAllUsers();

        // then
        assertThat(answer.size()).isEqualTo(2);
        assertThat(answer.getFirst().chatId()).isEqualTo(this.chatId);
        assertThat(answer.getLast().chatId()).isEqualTo(chatId2);
    }
}
