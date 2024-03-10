package edu.java.bot.controller;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.response.ApiErrorResponse;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

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
