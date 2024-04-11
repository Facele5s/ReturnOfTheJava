package edu.java.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Release {
    private long id;

    @JsonProperty("published_at")
    @Getter(AccessLevel.NONE)
    private String publishedAd;

    public OffsetDateTime getDate() {
        return OffsetDateTime.parse(publishedAd);
    }
}
