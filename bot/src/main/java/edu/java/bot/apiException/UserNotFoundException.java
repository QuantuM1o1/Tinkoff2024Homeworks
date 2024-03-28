package edu.java.bot.apiException;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;

public class UserNotFoundException
    extends jakarta.servlet.ServletException
    implements org.springframework.web.ErrorResponse {
    private final Long userId;

    public UserNotFoundException(long userId) {
        this.userId = userId;
    }

    @Override
    public @NotNull HttpStatusCode getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public @NotNull ProblemDetail getBody() {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "User wasn't found");
    }

    public long getId() {
        return this.userId;
    }
}
