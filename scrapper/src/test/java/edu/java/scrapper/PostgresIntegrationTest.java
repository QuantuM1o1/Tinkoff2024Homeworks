package edu.java.scrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PostgresIntegrationTest extends IntegrationTest {
    @Test
    @DisplayName("Имя")
    void name() {
        // given

        // when

        // then
        assertTrue(POSTGRES.isRunning());
    }
}
