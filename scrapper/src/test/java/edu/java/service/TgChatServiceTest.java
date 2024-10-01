package edu.java.service;

import edu.java.apiException.AlreadyRegisteredException;
import edu.java.dto.UserDTO;
import edu.java.repository.jdbc.JdbcUsersRepository;
import edu.java.scrapper.IntegrationTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
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
    private JdbcUsersRepository jdbcUserRepository;

    @Autowired
    private TgChatService tgChatService;

    private long id;

    @BeforeEach
    void setUp() {
        this.id = 1L;
    }

    @Test
    @DisplayName("Регистрация пользователя")
    public void registerUser() throws AlreadyRegisteredException {
        // given

        // when
        this.tgChatService.register(this.id);
        List<UserDTO> answer = this.jdbcUserRepository.findUserById(this.id);

        // then
        assertThat(answer.size()).isEqualTo(1);
        assertThat(answer.getFirst().tgChatId()).isEqualTo(this.id);
    }

    @Test
    @DisplayName("Удаление пользователя")
    public void unregisterUser() {
        // given

        // when
        this.jdbcUserRepository.addUser(this.id);
        this.tgChatService.unregister(this.id);
        List<UserDTO> answer = this.jdbcUserRepository.findUserById(this.id);

        // then
        assertThat(answer.size()).isEqualTo(0);
    }
}
