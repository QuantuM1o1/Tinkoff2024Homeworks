package dto;

import jakarta.validation.Valid;
import java.util.List;

/**
 * dto.ListLinksResponse
 */

public record ListLinksResponse(
    List<@Valid LinkResponse> links,
    int size
) {
}

