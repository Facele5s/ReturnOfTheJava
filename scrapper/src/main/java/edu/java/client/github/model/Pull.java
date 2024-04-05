package edu.java.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pull {
    private long id;

    @JsonProperty("created_at")
    @Getter(AccessLevel.NONE)
    private String createdAt;

    public OffsetDateTime getDate() {
        return OffsetDateTime.parse(createdAt);
    }
}
