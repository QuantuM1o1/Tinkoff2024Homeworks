package edu.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

public record StackOverflowQuestionResponse(
    List<Item> items
) {
    public record Item(
        @JsonProperty("question_id")
        long id,
        @JsonProperty("last_activity_date")
        OffsetDateTime lastActivityDate,
        String link
    ) {
    }
}
