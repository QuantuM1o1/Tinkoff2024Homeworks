package edu.java.dto;

import java.time.LocalDateTime;

public record LinkDTO(
    Long linkId,
    String url,
    LocalDateTime addedAt,
    LocalDateTime updatedAt
) {
}
