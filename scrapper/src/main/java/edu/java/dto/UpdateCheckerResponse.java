package edu.java.dto;

import java.time.OffsetDateTime;
import java.util.Optional;

public record UpdateCheckerResponse(
    Optional<String> description,
    OffsetDateTime lastActivity,
    int answerCount,
    int commentCount
) {
}
