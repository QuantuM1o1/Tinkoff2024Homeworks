package edu.java.apiException;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;

public class AlreadyRegisteredException
    extends jakarta.servlet.ServletException
    implements org.springframework.web.ErrorResponse {
    @Override
    public @NotNull HttpStatusCode getStatusCode() {
        return HttpStatus.CONFLICT;
    }

    @Override
    public @NotNull ProblemDetail getBody() {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "User has already registered");
    }
}
