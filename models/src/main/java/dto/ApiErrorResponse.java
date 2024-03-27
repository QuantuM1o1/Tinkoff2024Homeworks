package dto;

import java.util.List;

/**
 * dto.ApiErrorResponse
 */

public record ApiErrorResponse(
    String description,
    String code,
    String exceptionName,
    String exceptionMessage,
    List<String> stacktrace
) {
}

