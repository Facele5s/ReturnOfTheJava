package edu.java.dto.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends Exception {
    private final String description;

    public BadRequestException(String message, String description) {
        super(message);
        this.description = description;
    }
}
