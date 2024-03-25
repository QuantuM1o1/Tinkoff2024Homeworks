package edu.java.dao.jooq;

import edu.java.dto.UserDTO;
import edu.java.scrapper.IntegrationTest;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class JooqUserDAOTest extends IntegrationTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JooqUserDAO userRepository;

    @Test
    @DisplayName("Добавление пользователя в таблицу")
    @Transactional
    @Rollback
    void addTest() {
        // given
        long chatId = 123L;
        String sql = "SELECT * FROM users";

        // when
        userRepository.addUser(chatId);
        List<UserDTO> answer = jdbcTemplate.query(sql, new DataClassRowMapper<>(UserDTO.class));

        // then
        assertThat(answer.size()).isEqualTo(1);
        assertThat(answer.getFirst().chatId()).isEqualTo(chatId);
    }

    @Test
    @DisplayName("Удаление пользователя из таблицы")
    @Transactional
    @Rollback
    void removeTest() {
        // given
        long chatId1 = 123L;
        long chatId2 = 1231L;
        OffsetDateTime addedAt = OffsetDateTime.now();
        String sql = "SELECT * FROM users WHERE deleted_at IS NULL";
        String sqlDeleted = "SELECT * FROM users WHERE deleted_at IS NOT NULL";
        String sqlAdd = "INSERT INTO users (chat_id, added_at) VALUES (?, ?)";

        // when
        jdbcTemplate.update(sqlAdd, chatId1, addedAt);
        jdbcTemplate.update(sqlAdd, chatId2, addedAt);
        userRepository.removeUser(chatId1);
        List<UserDTO> answer = jdbcTemplate.query(sql, new DataClassRowMapper<>(UserDTO.class));
        List<UserDTO> deleted = jdbcTemplate.query(sqlDeleted, new DataClassRowMapper<>(UserDTO.class));

        // then
        assertThat(answer.size()).isEqualTo(1);
        assertThat(answer.getFirst().chatId()).isEqualTo(chatId2);
        assertThat(deleted.size()).isEqualTo(1);
        assertThat(deleted.getFirst().chatId()).isEqualTo(chatId1);
    }

    @Test
    @DisplayName("Чтение из таблицы")
    @Transactional
    @Rollback
    void findAllTest() {
        // given
        long chatId1 = 123L;
        long chatId2 = 1231L;
        OffsetDateTime addedAt = OffsetDateTime.now();
        String sqlAdd = "INSERT INTO users (chat_id, added_at) VALUES (?, ?)";

        // when
        jdbcTemplate.update(sqlAdd, chatId1, addedAt);
        jdbcTemplate.update(sqlAdd, chatId2, addedAt);
        List<UserDTO> answer = userRepository.findAllUsers();

        // then
        assertThat(answer.size()).isEqualTo(2);
        assertThat(answer.getFirst().chatId()).isEqualTo(chatId1);
        assertThat(answer.getLast().chatId()).isEqualTo(chatId2);
    }
}
