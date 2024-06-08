package edu.java.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {
    private String name;

    @Getter(AccessLevel.NONE)
    private String date;

    public OffsetDateTime getDate() {
        return OffsetDateTime.parse(date);
    }
}
