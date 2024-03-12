package edu.java.dto;

import java.time.LocalDateTime;

public record UserDTO(
    Long chatId,
    String username,
    LocalDateTime addedAt
) {
}
