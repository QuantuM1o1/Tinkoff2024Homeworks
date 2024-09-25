package edu.java.dto;

import java.time.OffsetDateTime;

public record LinkDTO(
    long id,
    String url,
    OffsetDateTime updatedAt,
    OffsetDateTime lastActivity,
    Integer answerCount,
    Integer commentCount,
    String domainName
) {
}
