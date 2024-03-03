package edu.java.bot.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import dto.ApiErrorResponse;
import edu.java.bot.apiException.UserNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(UserNotFoundException ex) {
        List<Long> fields = new ArrayList<>(ex.getIds());

        ApiErrorResponse response = new ApiErrorResponse()
            .description("Didn't find some users")
            .code("404")
            .exceptionMessage("Couldn't find users with ids " + fields)
            .exceptionName("Not Found");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}

