package edu.java.client.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.java.client.Response;
import edu.java.client.github.model.Owner;
import java.time.OffsetDateTime;
import java.util.Map;
import lombok.Data;

@Data
public class GitHubResponse implements Response {
    private final long id;

    private final String name;

    private final Owner owner;

    @JsonProperty("updated_at")
    private final OffsetDateTime updatedAt;

    @JsonProperty("pushed_at")
    private final OffsetDateTime pushedAt;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Map<String, String> getParams() {
        return Map.of("username", owner.getLogin(), "repoName", name);
    }

    @Override
    public OffsetDateTime getUpdateDate() {
        return pushedAt;
    }
}
