package edu.java.scrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PostgresIntegrationTest extends IntegrationTest {
    @Test
    @DisplayName("База данных запущена")
    void name() {
        // given

        // when

        // then
        assertTrue(POSTGRES.isRunning());
    }
}
