package edu.java.service;

import edu.java.apiException.AlreadyRegisteredException;
import edu.java.dto.UserDTO;
import edu.java.repository.jdbc.JdbcUserRepository;
import edu.java.scrapper.IntegrationTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class TgChatServiceTest extends IntegrationTest {
    @Autowired
    JdbcUserRepository jdbcUserRepository;

    @Autowired
    TgChatService tgChatService;

    @Test
    @DisplayName("Регистрация пользователя")
    public void registerUser() throws AlreadyRegisteredException {
        // given
        long id = 1L;

        // when
        tgChatService.register(id);
        List<UserDTO> answer = jdbcUserRepository.findUserById(id);

        // then
        assertThat(answer.size()).isEqualTo(1);
        assertThat(answer.getFirst().chatId()).isEqualTo(id);
    }

    @Test
    @DisplayName("Удаление пользователя")
    public void unregisterUser() {
        // given
        long id = 1L;

        // when
        jdbcUserRepository.addUser(id);
        tgChatService.unregister(id);
        List<UserDTO> answer = jdbcUserRepository.findUserById(id);

        // then
        assertThat(answer.size()).isEqualTo(0);
    }
}
