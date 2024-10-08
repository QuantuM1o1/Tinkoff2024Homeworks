package dto;

import java.util.List;
import lombok.Getter;

/**
 * dto.ApiErrorResponse
 */

@Getter
public class ApiErrorResponse extends Throwable {
    private final String description;
    private final String code;
    private final String exceptionName;
    private final String exceptionMessage;
    private final List<String> stacktrace;

    public ApiErrorResponse(
        String description, String code, String exceptionName, String exceptionMessage, List<String> stacktrace
    ) {
        super(exceptionMessage);

        this.description = description;
        this.code = code;
        this.exceptionName = exceptionName;
        this.exceptionMessage = exceptionMessage;
        this.stacktrace = stacktrace;
    }

    @Override
    public String toString() {
        return "{\"description\": \"" + description + "\","
            + "\"code\": \"" + code + "\""
            + "\"exception_name\": \"" + exceptionName + "\""
            + "\"exception_message\": \"" + exceptionMessage + "\""
            + "\"stacktrace\": \"" + stacktrace + "\"}";
    }
}

