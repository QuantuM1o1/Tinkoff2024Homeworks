package edu.java.service;

import edu.java.client.StackOverflowQuestionClient;
import edu.java.configuration.ResourcesConfig;
import edu.java.dto.LinkDTO;
import edu.java.dto.StackOverflowQuestionRequest;
import edu.java.dto.StackOverflowQuestionResponse;
import edu.java.dto.UpdateCheckerResponse;
import edu.java.repository.jdbc.JdbcLinkRepository;
import edu.java.service.updateChecker.StackOverflowUpdateChecker;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StackOverflowUpdateCheckerTest {
    @MockBean
    private StackOverflowQuestionClient mockClient;

    @MockBean
    private JdbcLinkRepository linkRepository;

    @Autowired
    private StackOverflowUpdateChecker updateChecker;

    @Test
    @DisplayName("Возврат ответа")
    public void returnAnswer() {
        // given
        String url = "https://stackoverflow.com/questions/31322043/project-euler-8-answer-fails-to-be-true";
        StackOverflowQuestionRequest mockRequest = new StackOverflowQuestionRequest("stackoverflow", 31322043);
        List<StackOverflowQuestionResponse.Item> itemList = new ArrayList<>();
        OffsetDateTime time = OffsetDateTime.MIN;
        OffsetDateTime lastActivity = OffsetDateTime.now();
        int answers = 2;
        int comments = 3;
        StackOverflowQuestionResponse.Item item
            = new StackOverflowQuestionResponse.Item(1, lastActivity, url, comments, answers);
        itemList.add(item);
        StackOverflowQuestionResponse mockResponse = new StackOverflowQuestionResponse(itemList);
        List<LinkDTO> list = new ArrayList<>();
        LinkDTO linkDTO = new LinkDTO(1, url, time, time, time, answers - 1, comments - 1, "domain");
        list.add(linkDTO);
        Mono<StackOverflowQuestionResponse> mockMono = Mockito.mock(Mono.class);

        // when
        when(this.mockClient.fetch(mockRequest)).thenReturn(mockMono);
        when(mockMono.block()).thenReturn(mockResponse);
        when(this.linkRepository.findLinkByUrl(url)).thenReturn(list);

        UpdateCheckerResponse answer = this.updateChecker.updateLink(url);

        // then
        assertThat(answer.description().get()).isEqualTo("New answer in StackOverflowQuestion");
        assertThat(answer.lastActivity()).isEqualTo(lastActivity);
        assertThat(answer.answerCount()).isEqualTo(answers);
        assertThat(answer.commentCount()).isEqualTo(comments);
    }
}
