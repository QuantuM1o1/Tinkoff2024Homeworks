package edu.java.controller;

import edu.java.service.LinkService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class LinksControllerTest {
    @MockBean private LinkService mockLinkService;
    @Autowired private MockMvc mockMvc;

    @Test
    @DisplayName("Удалить ссылку")
    public void deleteLink() throws Exception {
        // given

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete("/links")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Tg-Chat-Id", 1)
                .content("{\"link\":\"https://www.google.com/\"}"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Получить ссылки")
    public void getLinks() throws Exception {
        // given

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/links")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Tg-Chat-Id", 1))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Добавить ссылку")
    public void addLink() throws Exception {
        // given

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/links")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Tg-Chat-Id", 1)
                    .content("{\"link\":\"https://www.google.com/\"}"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
