package edu.java.client.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.java.client.Response;
import edu.java.client.github.model.Owner;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class GitHubResponse implements Response {
    private static final String KEY_LOGIN = "login";
    private static final String KEY_NAME = "name";

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
        Map<String, String> params = new HashMap<>();

        params.put(KEY_LOGIN, owner.getLogin());
        params.put(KEY_NAME, name);

        return params;
    }

    @Override
    public OffsetDateTime getUpdateDate() {
        return pushedAt;
    }
}
