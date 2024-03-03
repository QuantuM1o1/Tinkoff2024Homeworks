package edu.java.bot.apiExceptions;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;

public class UserNotFoundException
    extends jakarta.servlet.ServletException
    implements org.springframework.web.ErrorResponse {
    private final List<Long> usersList;

    public UserNotFoundException(List<Long> usersList) {
        this.usersList = usersList;
    }

    @Override
    public @NotNull HttpStatusCode getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public @NotNull ProblemDetail getBody() {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "User wasn't found");
    }

    public List<Long> getIds() {
        return usersList;
    }
}
