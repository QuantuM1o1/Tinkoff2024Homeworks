package edu.java.dto;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class StackOverflowDTOTest {
    @Test
    @DisplayName("Перевод даты Epoch -> OffsetDateTime")
    public void epochToOffsetDateTimeConversion() {
        // given
        List<StackOverflowDTO.Item> items = new ArrayList<>();
        StackOverflowDTO.Item item = new StackOverflowDTO.Item(123L, 1283320547L, "link");
        items.add(item);
        StackOverflowDTO test = new StackOverflowDTO(items);

        // when
        OffsetDateTime answer = test.items().getFirst().returnLastActivityInDateTime();

        // then
        assertThat(answer).isEqualTo("2010-09-01T05:55:47Z");
    }
}
