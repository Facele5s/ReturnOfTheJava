package edu.java.client.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.java.client.Response;
import java.time.OffsetDateTime;

public record GitHubResponse(
    long id,

    String name,

    @JsonProperty("updated_at")
    OffsetDateTime updatedAt,

    @JsonProperty("pushed_at")
    OffsetDateTime pushedAt
) implements Response {

}
