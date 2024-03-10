package edu.java.scrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class PostgresIntegrationTest {
    @Test
    @DisplayName("Имя")
    void name() {
        // given

        // when

        // then
        assertThat(IntegrationTest.POSTGRES.getDatabaseName()).isEqualTo("scrapper");
    }
}
