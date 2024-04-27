package edu.java.bot.controller;

import edu.java.bot.service.UpdateNotifier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.Mockito.doNothing;

@WebMvcTest(UpdatesController.class)
@AutoConfigureMockMvc
public class UpdatesControllerTest {
    private AutoCloseable mocks;

    @MockBean
    private UpdateNotifier mockNotifier;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        this.mocks.close();
    }

    @Test
    @DisplayName("Все пользователи получили обновление")
    public void allUsersWereNotified() throws Exception {
        // given
        String body = "{\"id\":\"1\",\"url\":\"https://stackoverflow.com/\",\"description\":\"something new\",\"tgChatIds\":[1]}";

        // when
        doNothing().when(this.mockNotifier);

        // then
        this.mockMvc.perform(MockMvcRequestBuilders.post("/updates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
