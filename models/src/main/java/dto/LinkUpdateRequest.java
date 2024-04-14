package dto;

import java.net.URI;
import java.util.List;

/**
 * dto.LinkUpdateRequest
 */

public record LinkUpdateRequest(
    long id,
    URI url,
    String description,
    List<Long> tgChatIds
) {
}

