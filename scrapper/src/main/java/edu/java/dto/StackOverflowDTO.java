package edu.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

public record StackOverflowDTO(
    List<Item> items
) {
    public record Item(
        @JsonProperty("question_id")
        long id,
        @JsonProperty("last_activity_date")
        long lastActivityDate,
        String link
    ) {
        public OffsetDateTime returnLastActivityInDateTime() {
            return OffsetDateTime.ofInstant(Instant.ofEpochSecond(this.lastActivityDate), ZoneOffset.UTC);
        }
    }
}
