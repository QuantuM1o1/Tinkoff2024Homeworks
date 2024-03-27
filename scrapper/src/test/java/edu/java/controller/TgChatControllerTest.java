package edu.java.controller;

import edu.java.apiException.AlreadyRegisteredException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TgChatControllerTest {
    private final TgChatController controller = new TgChatController();

    @Test
    @DisplayName("Удалить чат")
    public void deleteChat() {
        // given
        Long tgChatId = 1L;

        // when
        ResponseEntity<Void> response = controller.deleteTgChatId(tgChatId);

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
        ResponseEntity<Void> response = controller.postTgChatId(tgChatId);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
