package edu.java.dto.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends Exception {
    private final String description;

    public NotFoundException(String message, String description) {
        super(message);
        this.description = description;
    }
}
