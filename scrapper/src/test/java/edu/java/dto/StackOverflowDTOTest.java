package edu.java.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class StackOverflowDTOTest {
    @Test
    @DisplayName("Создание DTO")
    public void create() {
        // given
        String test =
            "{\"items\":[{\"last_activity_date\":1283320547,\"question_id\":3615006,\"link\":\"https://stackoverflow.com/questions/3615006/unit-tests-must-locate-in-the-same-package\"}]}";

        // when
        StackOverflowDTO answer;
        try {
            answer = StackOverflowDTO.fromJson(test);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // then
        assertThat(answer.getLink()).isEqualTo("https://stackoverflow.com/questions/3615006/unit-tests-must-locate-in-the-same-package");
        assertThat(answer.getId()).isEqualTo(3615006);
        assertThat(answer.getLastActivityDate()).isEqualTo("2024-02-18T12:43:36Z");
    }
}
