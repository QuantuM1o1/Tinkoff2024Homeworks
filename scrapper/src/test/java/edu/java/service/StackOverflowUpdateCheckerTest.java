package edu.java.service;

import edu.java.client.StackOverflowQuestionClient;
import edu.java.configuration.ResourcesConfig;
import edu.java.dto.LinkDTO;
import edu.java.dto.StackOverflowQuestionRequest;
import edu.java.dto.StackOverflowQuestionResponse;
import edu.java.property.SupportedResource;
import edu.java.repository.LinkRepository;
import edu.java.service.updateChecker.StackOverflowUpdateChecker;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = StackOverflowUpdateChecker.class)
public class StackOverflowUpdateCheckerTest {
    @MockBean
    private StackOverflowQuestionClient mockClient;

    @MockBean
    private LinkRepository linkRepository;

    @MockBean
    private ResourcesConfig resourcesConfig;

    @Autowired
    private StackOverflowUpdateChecker updateChecker;

    @Test
    @DisplayName("Возврат сообщения")
    public void returnDescription() {
        // given
        String url = "https://stackoverflow.com/questions/31322043/project-euler-8-answer-fails-to-be-true";
        StackOverflowQuestionRequest mockRequest = new StackOverflowQuestionRequest("stackoverflow", 31322043);
        List<StackOverflowQuestionResponse.Item> itemList = new ArrayList<>();
        StackOverflowQuestionResponse.Item item
            = new StackOverflowQuestionResponse.Item(1, OffsetDateTime.now(), "link");
        itemList.add(item);
        StackOverflowQuestionResponse mockResponse = new StackOverflowQuestionResponse(itemList);
        List<LinkDTO> list = new ArrayList<>();
        LinkDTO linkDTO = new LinkDTO(
            1,
            url,
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            "domain");
        list.add(linkDTO);
        Mono<StackOverflowQuestionResponse> mockMono = Mockito.mock(Mono.class);
        SupportedResource mockResource = new SupportedResource(
            2,
            "https://api.stackexchange.com/2.3",
            "^https?://stackoverflow\\.com/.*/(.*)/.*$"
        );
        Map<String, SupportedResource> mockMap = new HashMap<>();
        mockMap.put("domain", mockResource);

        // when
        when(mockClient.fetch(mockRequest)).thenReturn(mockMono);
        when(mockMono.block()).thenReturn(mockResponse);
        when(linkRepository.findLinkByUrl(url)).thenReturn(list);
        when(resourcesConfig.supportedResources()).thenReturn(mockMap);
        Optional<String> answer = updateChecker.description(url);

        // then
        assertFalse(answer.isEmpty());
        assertThat(answer.get()).isEqualTo("New answer in StackOverflowQuestion");
    }

    @Test
    @DisplayName("Возврат даты")
    public void returnDateTime() {
        // given
        String url = "https://stackoverflow.com/questions/31322043/project-euler-8-answer-fails-to-be-true";
        StackOverflowQuestionRequest mockRequest = new StackOverflowQuestionRequest("stackoverflow", 31322043);
        List<StackOverflowQuestionResponse.Item> itemList = new ArrayList<>();
        OffsetDateTime test = OffsetDateTime.now();
        StackOverflowQuestionResponse.Item item
            = new StackOverflowQuestionResponse.Item(1, test, "link");
        itemList.add(item);
        StackOverflowQuestionResponse mockResponse = new StackOverflowQuestionResponse(itemList);
        Mono<StackOverflowQuestionResponse> mockMono = Mockito.mock(Mono.class);
        SupportedResource mockResource = new SupportedResource(
            2,
            "https://api.stackexchange.com/2.3",
            "^https?://stackoverflow\\.com/.*/(.*)/.*$"
        );
        Map<String, SupportedResource> mockMap = new HashMap<>();
        mockMap.put("domain", mockResource);

        // when
        when(mockClient.fetch(mockRequest)).thenReturn(mockMono);
        when(mockMono.block()).thenReturn(mockResponse);
        when(resourcesConfig.supportedResources()).thenReturn(mockMap);
        OffsetDateTime answer = updateChecker.getLastActivity(url, "domain");

        // then
        assertThat(answer).isEqualTo(test);
    }
}
