package edu.java.controller;

import edu.java.dto.LinkDTO;
import edu.java.service.LinkService;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class LinksControllerTest {
    private AutoCloseable mocks;

    @MockBean
    private LinkService mockLinkService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    @DisplayName("Удалить ссылку")
    public void deleteLink() throws Exception {
        // given


        // when
        doNothing().when(mockLinkService);

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
        long tgChatId = 1L;
        Collection<LinkDTO> collection = new ArrayList<>();

        // when
        when(mockLinkService.listAll(tgChatId)).thenReturn(collection);

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
        doNothing().when(mockLinkService);

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/links")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Tg-Chat-Id", 1)
                    .content("{\"link\":\"https://www.google.com/\"}"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
