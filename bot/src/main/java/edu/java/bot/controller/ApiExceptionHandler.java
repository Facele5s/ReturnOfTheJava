package edu.java.bot.controller;

import edu.java.dto.exception.BadRequestException;
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
}
