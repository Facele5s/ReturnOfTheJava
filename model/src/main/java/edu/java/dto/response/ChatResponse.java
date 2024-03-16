package edu.java.dto.response;

import java.time.OffsetDateTime;

public record ChatResponse(
    Long id,
    OffsetDateTime registrationDate
) {
}
