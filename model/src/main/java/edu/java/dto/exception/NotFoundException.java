package edu.java.dto.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {
    private final String description;

    public NotFoundException(String message, String description) {
        super(message);
        this.description = description;
    }
}
