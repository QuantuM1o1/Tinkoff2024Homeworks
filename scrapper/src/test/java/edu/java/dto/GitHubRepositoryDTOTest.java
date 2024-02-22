package edu.java.dto;

import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GitHubRepositoryDTOTest {
    @Test
    @DisplayName("Создание DTO")
    public void create() {
        // given
        String test =
            "{\"id\": 1296269,\"full_name\": \"octocat/Hello-World\",\"updated_at\": \"2024-02-18T12:43:36Z\"}";

        // when
        GitHubRepositoryDTO answer;
        try {
            answer = GitHubRepositoryDTO.fromJson(test);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // then
        assertThat(answer.getFullName()).isEqualTo("octocat/Hello-World");
        assertThat(answer.getId()).isEqualTo(1296269);
        assertThat(answer.getUpdatedAt()).isEqualTo("2024-02-18T12:43:36Z");
    }
}
