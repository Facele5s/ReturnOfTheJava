package edu.java.entity;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GithubCommit {
    private String sha;

    private Long repoId;

    private String author;

    private OffsetDateTime createdAt;
}
