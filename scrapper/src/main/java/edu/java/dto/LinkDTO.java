package edu.java.dto;

import java.time.OffsetDateTime;

public record LinkDTO(
    long linkId,
    String url,
    OffsetDateTime addedAt,
    OffsetDateTime updatedAt,
    OffsetDateTime lastActivity,
    String domainName
) {
}
