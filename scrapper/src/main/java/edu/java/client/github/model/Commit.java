package edu.java.client.github.model;

import java.time.OffsetDateTime;

public class Commit {
    private String sha;
    private CommitInfo commit;

    public OffsetDateTime getDate() {
        return commit.getAuthor().getDate();
    }
}
