package edu.java.controller;

import edu.java.apiException.AlreadyRegisteredException;
import edu.java.service.TgChatService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doNothing;

public class TgChatControllerTest {
    private AutoCloseable closeable;
    @InjectMocks
    private final TgChatController controller = new TgChatController();

    @Mock
    TgChatService mockChatService;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        doNothing().when(mockChatService);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

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
    public void addChat() throws AlreadyRegisteredException {
        // given
        Long tgChatId = 1L;

        // when
        ResponseEntity<Void> response = controller.postTgChatId(tgChatId);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
