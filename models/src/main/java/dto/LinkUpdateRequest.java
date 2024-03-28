package dto;

import java.net.URI;
import java.util.List;

/**
 * dto.LinkUpdateRequest
 */

public record LinkUpdateRequest(
    Long id,
    URI url,
    String description,
    List<Long> tgChatIds
) {
}

