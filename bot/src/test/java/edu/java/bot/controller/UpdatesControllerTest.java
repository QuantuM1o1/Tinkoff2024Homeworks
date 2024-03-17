package edu.java.bot.controller;

import dto.LinkUpdateRequest;
import edu.java.bot.apiException.UserNotFoundException;
import edu.java.bot.dto.ChatUser;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UpdatesControllerTest {
    private AutoCloseable closeable;
    @Mock
    private Map<Long, ChatUser> usersMap;

    @InjectMocks
    private UpdatesController updatesController;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    @DisplayName("Все пользователи получили обновление")
    public void allUsersWereNotified() throws UserNotFoundException {
        // given
        List<Long> chatIds = new ArrayList<>();
        chatIds.add(1L);
        chatIds.add(2L);
        when(usersMap.containsKey(1L)).thenReturn(true);
        when(usersMap.containsKey(2L)).thenReturn(true);
        LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest();
        linkUpdateRequest.setTgChatIds(chatIds);

        // when
        ResponseEntity<Void> response = updatesController.updatesPost(linkUpdateRequest);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Один из пользователей не найден")
    public void oneUserNotFound() {
        // given
        List<Long> chatIds = new ArrayList<>();
        chatIds.add(1L);
        chatIds.add(2L);
        when(usersMap.containsKey(1L)).thenReturn(true);
        LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest();
        linkUpdateRequest.setTgChatIds(chatIds);

        // when

        // then
        assertThrows(UserNotFoundException.class, () -> updatesController.updatesPost(linkUpdateRequest));
    }
}
