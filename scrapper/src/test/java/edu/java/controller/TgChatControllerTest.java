package edu.java.controller;

import edu.java.service.TgChatService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@AutoConfigureMockMvc
public class TgChatControllerTest {
    private AutoCloseable mocks;

    @MockBean
    TgChatService mockChatService;

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
    @DisplayName("Удалить чат")
    public void deleteChat() throws Exception {
        // given
        long chatId = 1L;

        // when
        doNothing().when(this.mockChatService);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete("/tg-chat/{id}", chatId))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Добавить чат")
    public void addChat() throws Exception {
        // given
        long chatId = 1L;

        // when
        doNothing().when(this.mockChatService);

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/tg-chat/{id}", chatId))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
