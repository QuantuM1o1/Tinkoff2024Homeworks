package edu.java.service;

import edu.java.client.GitHubRepositoriesClient;
import edu.java.configuration.ResourcesConfig;
import edu.java.dto.GitHubRepositoryRequest;
import edu.java.dto.GitHubRepositoryResponse;
import edu.java.dto.LinkDTO;
import edu.java.dto.UpdateCheckerResponse;
import edu.java.repository.jdbc.JdbcLinkRepository;
import edu.java.service.updateChecker.GithubUpdateChecker;
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
public class GithubUpdateCheckerTest {
    @MockBean
    private GitHubRepositoriesClient mockClient;

    @MockBean
    private JdbcLinkRepository linkRepository;

    @Autowired
    private GithubUpdateChecker updateChecker;

    @Test
    @DisplayName("Возврат ответа")
    public void returnAnswer() {
        // given
        String url = "https://github.com/octocat/Hello-World";
        GitHubRepositoryRequest mockRequest = new GitHubRepositoryRequest("octocat", "Hello-World");
        OffsetDateTime time = OffsetDateTime.MIN;
        OffsetDateTime lastActivity = OffsetDateTime.now();
        GitHubRepositoryResponse mockResponse = new GitHubRepositoryResponse(1, "123", lastActivity);
        int count = 0;
        List<LinkDTO> list = new ArrayList<>();
        LinkDTO linkDTO = new LinkDTO(1, url, time, time, time, count, count, "github.com");
        list.add(linkDTO);
        Mono<GitHubRepositoryResponse> mockMono = Mockito.mock(Mono.class);

        // when
        when(this.mockClient.fetch(mockRequest)).thenReturn(mockMono);
        when(mockMono.block()).thenReturn(mockResponse);
        when(this.linkRepository.findLinkByUrl(url)).thenReturn(list);
        UpdateCheckerResponse answer = this.updateChecker.updateLink(url);

        // then
        assertThat(answer.description().get()).isEqualTo("New activity in GitHub repo");
        assertThat(answer.lastActivity()).isEqualTo(lastActivity);
        assertThat(answer.answerCount()).isEqualTo(count);
        assertThat(answer.commentCount()).isEqualTo(count);
    }
}
