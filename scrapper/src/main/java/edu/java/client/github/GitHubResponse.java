package edu.java.client.github;

import edu.java.client.Response;
import edu.java.client.github.model.Owner;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class GitHubResponse implements Response {
    private static final String KEY_LOGIN = "login";
    private static final String KEY_NAME = "name";

    private long id;
    private String name;
    private Owner owner;

    private final List<String> updateReasons;
    private final OffsetDateTime dateTime;

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
    public OffsetDateTime getUpdateDate() { //TODO
        return OffsetDateTime.now().minusDays(1);
    }

    @Override
    public String getDescription() {
        StringBuilder sb = new StringBuilder();

        updateReasons.forEach(r -> sb.append(r).append("\n"));

        return sb.toString();
    }
}
