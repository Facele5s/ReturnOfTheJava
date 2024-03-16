package edu.java.entity;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Chat {
    private Long id;
    private OffsetDateTime registrationDate;
}
