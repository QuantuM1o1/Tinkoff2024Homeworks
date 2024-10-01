package edu.java.repository.jdbc;

import edu.java.dto.UserDTO;
import edu.java.scrapper.IntegrationTest;
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
    private JdbcUsersRepository userRepository;

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
        assertThat(answer.getFirst().tgChatId()).isEqualTo(this.chatId);
    }

    @Test
    @DisplayName("Удаление пользователя из таблицы")
    void removeTest() {
        // given
        long chatId2 = 1231L;
        String sql = "SELECT * FROM users";
        String sqlAdd = "INSERT INTO users (tg_chat_id) VALUES (?)";

        // when
        jdbcTemplate.update(sqlAdd, this.chatId);
        jdbcTemplate.update(sqlAdd, chatId2);
        userRepository.removeUser(this.chatId);
        List<UserDTO> answer = jdbcTemplate.query(sql, new DataClassRowMapper<>(UserDTO.class));

        // then
        assertThat(answer.size()).isEqualTo(1);
        assertThat(answer.getFirst().tgChatId()).isEqualTo(chatId2);
    }

    @Test
    @DisplayName("Чтение из таблицы")
    void findAllTest() {
        // given
        long chatId2 = 1231L;
        String sqlAdd = "INSERT INTO users (tg_chat_id) VALUES (?)";

        // when
        jdbcTemplate.update(sqlAdd, this.chatId);
        jdbcTemplate.update(sqlAdd, chatId2);
        List<UserDTO> answer = userRepository.findAllUsers();

        // then
        assertThat(answer.size()).isEqualTo(2);
        assertThat(answer.getFirst().tgChatId()).isEqualTo(this.chatId);
        assertThat(answer.getLast().tgChatId()).isEqualTo(chatId2);
    }
}
