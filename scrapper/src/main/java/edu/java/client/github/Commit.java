package edu.java.client.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import java.time.OffsetDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Commit {
    private String sha;
    private CommitInfo commit;

    public OffsetDateTime getDate() {
        return commit.getAuthor().getDate();
    };
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class CommitInfo {
    private Author author;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class Author {
    private String name;

    @Getter(AccessLevel.NONE)
    private String date;

    public OffsetDateTime getDate() {
        return OffsetDateTime.parse(date);
    }
}






