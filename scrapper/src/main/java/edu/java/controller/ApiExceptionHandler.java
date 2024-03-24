package edu.java.controller;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.dto.response.ApiErrorResponse;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(NotFoundException e) {
        return new ResponseEntity<>(
            convertException(e, HttpStatus.NOT_FOUND, e.getDescription()),
            HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequest(BadRequestException e) {
        return new ResponseEntity<>(
            convertException(e, HttpStatus.BAD_REQUEST, e.getDescription()),
            HttpStatus.BAD_REQUEST
        );
    }

    private ApiErrorResponse convertException(Exception e, HttpStatus status, String description) {
        return new ApiErrorResponse(
            description,
            status.getReasonPhrase(),
            e.getClass().getSimpleName(),
            e.getMessage(),
            Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .toList()
        );
    }
}
