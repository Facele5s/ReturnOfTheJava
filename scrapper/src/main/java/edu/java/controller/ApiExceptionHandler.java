package edu.java.controller;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.dto.response.ApiErrorResponse;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ApiErrorResponse handleBadRequest(BadRequestException e) {
        return new ApiErrorResponse(
            e.getDescription(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            e.getClass().getSimpleName(),
            e.getMessage(),
            Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .toList()
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ApiErrorResponse handleNotFound(NotFoundException e) {
        return new ApiErrorResponse(
            e.getDescription(),
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            e.getClass().getSimpleName(),
            e.getMessage(),
            Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .toList()
        );
    }
}
