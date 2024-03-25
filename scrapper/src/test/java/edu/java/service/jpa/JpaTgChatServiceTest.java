package edu.java.service.jpa;

import edu.java.dto.UserDTO;
import edu.java.repository.JpaUserRepository;
import edu.java.scrapper.IntegrationTest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JpaTgChatServiceTest extends IntegrationTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    JpaUserRepository userRepository;

    private JpaTgChatService jpaTgChatService;

    @BeforeEach
    void setUp() {
        this.jpaTgChatService = new JpaTgChatService(this.userRepository);
    }

    @Test
    @DisplayName("Добавление пользователя")
    @Transactional
    @Rollback
    void registerTest() {
        // given
        long id = 123L;
        String sql = "SELECT * FROM users";

        // when
        this.jpaTgChatService.register(id);
        List<UserDTO> answer =  this.jdbcTemplate.query(sql, new DataClassRowMapper<>(UserDTO.class));

        // then
        assertThat(answer.size()).isEqualTo(1);
        assertThat(answer.getFirst().chatId()).isEqualTo(id);
    }

    @Test
    @DisplayName("Удаление пользователя")
    @Transactional
    @Rollback
    void unregisterTest() {
        // given
        String insert = "INSERT INTO users (chat_id, added_at) VALUES (?, ?)";
        String select = "SELECT * FROM users";
        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);
        long tgChatId = 123L;

        // when
        this.jdbcTemplate.update(insert, tgChatId, timestamp);
        List<UserDTO> answer =  this.jdbcTemplate.query(select, new DataClassRowMapper<>(UserDTO.class));
        assertThat(answer.size()).isEqualTo(1);

        this.jpaTgChatService.unregister(tgChatId);
        answer =  this.jdbcTemplate.query(select, new DataClassRowMapper<>(UserDTO.class));

        // then
        assertThat(answer.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Проверка, что уже зарегистрирован")
    @Transactional
    @Rollback
    void checkTest() {
        // given
        String insert = "INSERT INTO users (chat_id, added_at) VALUES (?, ?)";
        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);
        long tgChatId = 123L;

        // when
        boolean answer = this.jpaTgChatService.checkIfAlreadyRegistered(tgChatId);
        assertFalse(answer);

        this.jdbcTemplate.update(insert, tgChatId, timestamp);
        answer = this.jpaTgChatService.checkIfAlreadyRegistered(tgChatId);

        // then
        assertTrue(answer);
    }
}
