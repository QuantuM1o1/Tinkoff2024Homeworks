package edu.java.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import edu.java.apiExceptions.AlreadyRegisteredException;
import edu.java.apiExceptions.LinkAlreadyExistsException;
import dto.ApiErrorResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(JsonMappingException ex) {
        List<String> errors = new ArrayList<>();
        List<String> fields = new ArrayList<>();

        ex.getPath().forEach(reference -> {
            fields.add(reference.getFieldName());
            String message = ex.getOriginalMessage();
            errors.add(message);
        });

        ApiErrorResponse response = new ApiErrorResponse()
            .description("Validation failed")
            .code("400")
            .stacktrace(errors)
            .exceptionMessage("Couldn't read property of " + fields)
            .exceptionName("Bad request");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(NoResourceFoundException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getResourcePath());

        ApiErrorResponse response = new ApiErrorResponse()
            .description("Link was not found")
            .code("404")
            .stacktrace(errors)
            .exceptionMessage("Couldn't find link")
            .exceptionName("Not found");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(AlreadyRegisteredException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(AlreadyRegisteredException ex) {
        List<String> errors = new ArrayList<>();

        ApiErrorResponse response = new ApiErrorResponse()
            .description("User with this id has already registered")
            .code(String.valueOf(ex.getStatusCode().value()))
            .stacktrace(errors)
            .exceptionMessage("User has already registered")
            .exceptionName("Conflict in user's id");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(LinkAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(LinkAlreadyExistsException ex) {
        List<String> errors = new ArrayList<>();

        ApiErrorResponse response = new ApiErrorResponse()
            .description("Link already exists for this user")
            .code(String.valueOf(ex.getStatusCode().value()))
            .stacktrace(errors)
            .exceptionMessage("Link already exists")
            .exceptionName("Conflict in link");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}

