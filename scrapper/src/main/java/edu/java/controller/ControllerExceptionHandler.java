package edu.java.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import dto.ApiErrorResponse;
import edu.java.apiException.AlreadyRegisteredException;
import edu.java.apiException.LinkAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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
    @ExceptionHandler(NoResourceFoundException.class)
    public ApiErrorResponse handleValidationException(NoResourceFoundException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getResourcePath());

        return new ApiErrorResponse(
            "Link was not found",
            "404",
            "Not found",
            "Couldn't find link",
            errors
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AlreadyRegisteredException.class)
    public ApiErrorResponse handleValidationException(AlreadyRegisteredException ex) {
        List<String> errors = new ArrayList<>();

        return new ApiErrorResponse(
            "User with this id has already registered",
            String.valueOf(ex.getStatusCode().value()),
            "Conflict in user's id",
            "User has already registered",
            errors
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(LinkAlreadyExistsException.class)
    public ApiErrorResponse handleValidationException(LinkAlreadyExistsException ex) {
        List<String> errors = new ArrayList<>();

        return new ApiErrorResponse(
            "Link already exists for this user",
            String.valueOf(ex.getStatusCode().value()),
            "Conflict in link",
            "Link already exists",
            errors
        );
    }
}

