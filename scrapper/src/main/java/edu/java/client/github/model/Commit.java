package edu.java.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Commit {
    private String sha;
    private CommitInfo commit;

    public OffsetDateTime getDate() {
        return commit.getAuthor().getDate();
    }

    public String getAuthor() {
        return commit.getAuthor().getName();
    }
}
