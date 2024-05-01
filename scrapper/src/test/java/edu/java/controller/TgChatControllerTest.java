package edu.java.controller;

import edu.java.service.TgChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class TgChatControllerTest {
    @MockBean
    private TgChatService mockChatService;

    @Autowired
    private MockMvc mockMvc;

    private long chatId;

    @BeforeEach
    public void setUp() {
        this.chatId = 1L;
    }

    @Test
    @DisplayName("Удалить чат")
    public void deleteChat() throws Exception {
        // given

        // when

        // then
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/tg-chat/{id}", this.chatId))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Добавить чат")
    public void addChat() throws Exception {
        // given

        // when

        // then
        this.mockMvc.perform(MockMvcRequestBuilders.post("/tg-chat/{id}", this.chatId))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
