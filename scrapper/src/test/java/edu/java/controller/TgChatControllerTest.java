package edu.java.controller;

import edu.java.apiException.AlreadyRegisteredException;
import edu.java.service.jdbc.JdbcTgChatService;
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
import static org.mockito.Mockito.when;

public class TgChatControllerTest {
    private AutoCloseable closeable;
    @InjectMocks
    private final TgChatController controller = new TgChatController();

    @Mock
    JdbcTgChatService mockChatService;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    @DisplayName("Удалить чат")
    public void deleteChat() {
        // given
        long tgChatId = 1L;

        // when
        doNothing().when(mockChatService);
        ResponseEntity<Void> response = controller.tgChatIdDelete(tgChatId);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Добавить чат")
    public void addChat() throws AlreadyRegisteredException {
        // given
        long tgChatId = 1L;

        // when
        doNothing().when(mockChatService).register(tgChatId);
        when(mockChatService.checkIfAlreadyRegistered(tgChatId)).thenReturn(false);
        ResponseEntity<Void> response = controller.tgChatIdPost(tgChatId);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
