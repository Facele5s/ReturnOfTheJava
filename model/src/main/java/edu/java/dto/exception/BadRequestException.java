package edu.java.dto.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends Exception {
    private final String description;

    public BadRequestException(String message, String description) {
        super(message);
        this.description = description;
    }
}
