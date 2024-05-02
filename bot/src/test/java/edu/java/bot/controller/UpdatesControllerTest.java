package edu.java.bot.controller;

import edu.java.bot.service.UpdateNotifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(UpdatesController.class)
@AutoConfigureMockMvc
public class UpdatesControllerTest {
    @MockBean
    private UpdateNotifier mockNotifier;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Все пользователи получили обновление")
    public void allUsersWereNotified() throws Exception {
        // given
        String body = "{\"id\":\"1\",\"url\":\"https://stackoverflow.com/\",\"description\":\"something new\",\"tgChatIds\":[1]}";

        // when

        // then
        this.mockMvc.perform(MockMvcRequestBuilders.post("/updates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
