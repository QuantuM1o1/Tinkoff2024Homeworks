package dto;

import java.net.URI;

/**
 * dto.LinkResponse
 */

public record LinkResponse(
    Long id,
    URI url
) {
}

