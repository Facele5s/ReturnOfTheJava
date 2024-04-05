package edu.java.client.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import java.time.OffsetDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Release {
    private long id;

    @JsonProperty("published_at")
    @Getter(AccessLevel.NONE)
    private String publishedAt;

    public OffsetDateTime getDate() {
        return OffsetDateTime.parse(publishedAt);
    }
}
