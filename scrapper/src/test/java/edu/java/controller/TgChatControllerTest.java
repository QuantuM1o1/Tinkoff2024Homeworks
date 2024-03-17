package edu.java.controller;

import edu.java.apiException.AlreadyRegisteredException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class TgChatControllerTest {
    @Autowired
    private TgChatController controller;

    @Test
    @DisplayName("Удалить чат")
    public void deleteChat() {
        // given
        Long tgChatId = 1L;

        // when
        ResponseEntity<Void> response = controller.tgChatIdDelete(tgChatId);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Добавить чат")
    public void addChat() throws AlreadyRegisteredException
    {
        // given
        Long tgChatId = 1L;

        // when
        ResponseEntity<Void> response = controller.tgChatIdPost(tgChatId);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
