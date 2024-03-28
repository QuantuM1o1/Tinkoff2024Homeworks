package edu.java.bot.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import dto.ApiErrorResponse;
import edu.java.bot.apiException.UserNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ControllerExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JsonMappingException.class)
    public ApiErrorResponse handleValidationException(JsonMappingException ex) {
        List<String> errors = new ArrayList<>();
        List<String> fields = new ArrayList<>();

        ex.getPath().forEach(reference -> {
            fields.add(reference.getFieldName());
            String message = ex.getOriginalMessage();
            errors.add(message);
        });

        return new ApiErrorResponse(
            "Validation failed",
            "400",
            "Bad request",
            "Couldn't read property of " + fields,
            errors
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ApiErrorResponse handleValidationException(UserNotFoundException ex) {
        return new ApiErrorResponse(
            "Didn't find some users",
            "404",
            "Not Found",
            "Couldn't find user with id " + ex.getId(),
            null
        );
    }
}

