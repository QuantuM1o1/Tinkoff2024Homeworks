package edu.java.service;

import edu.java.client.GitHubRepositoriesClient;
import edu.java.configuration.ResourcesConfig;
import edu.java.dto.GitHubRepositoryRequest;
import edu.java.dto.GitHubRepositoryResponse;
import edu.java.dto.LinkDTO;
import edu.java.property.SupportedResource;
import edu.java.repository.LinkRepository;
import edu.java.service.updateChecker.GithubUpdateChecker;
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

@SpringBootTest(classes = GithubUpdateChecker.class)
public class GithubUpdateCheckerTest {
    @MockBean
    private GitHubRepositoriesClient mockClient;

    @MockBean
    private LinkRepository linkRepository;

    @MockBean
    private ResourcesConfig resourcesConfig;

    @Autowired
    private GithubUpdateChecker updateChecker;

    @Test
    @DisplayName("Возврат сообщения")
    public void returnDescription() {
        // given
        String url = "https://github.com/octocat/Hello-World";
        GitHubRepositoryRequest mockRequest = new GitHubRepositoryRequest("octocat", "Hello-World");
        GitHubRepositoryResponse mockResponse = new GitHubRepositoryResponse(1, "123", OffsetDateTime.now());
        List<LinkDTO> list = new ArrayList<>();
        LinkDTO linkDTO = new LinkDTO(
            1,
            url,
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            "domain");
        list.add(linkDTO);
        Mono<GitHubRepositoryResponse> mockMono = Mockito.mock(Mono.class);
        SupportedResource mockResource = new SupportedResource(
            2,
            "https://api.github.com",
            "^https?://github\\.com/(.*)/(.*)$"
        );
        Map<String, SupportedResource> mockMap = new HashMap<>();
        mockMap.put(linkDTO.domainName(), mockResource);

        // when
        when(mockClient.fetch(mockRequest)).thenReturn(mockMono);
        when(mockMono.block()).thenReturn(mockResponse);
        when(linkRepository.findLinkByUrl(url)).thenReturn(list);
        when(resourcesConfig.supportedResources()).thenReturn(mockMap);
        Optional<String> answer = updateChecker.description(url);

        // then
        assertFalse(answer.isEmpty());
        assertThat(answer.get()).isEqualTo("New activity in GitHub repo");
    }

    @Test
    @DisplayName("Возврат даты")
    public void returnDateTime() {
        // given
        String url = "https://github.com/octocat/Hello-World";
        GitHubRepositoryRequest mockRequest = new GitHubRepositoryRequest("octocat", "Hello-World");
        OffsetDateTime test = OffsetDateTime.now();
        GitHubRepositoryResponse mockResponse = new GitHubRepositoryResponse(1, "123", test);
        Mono<GitHubRepositoryResponse> mockMono = Mockito.mock(Mono.class);
        SupportedResource mockResource = new SupportedResource(
            2,
            "https://api.github.com",
            "^https?://github\\.com/(.*)/(.*)$"
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
