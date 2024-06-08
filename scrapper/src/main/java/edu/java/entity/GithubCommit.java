package edu.java.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "github_commit")
public class GithubCommit {
    @Id
    private String sha;

    @Column(name = "repo_id")
    private Long repoId;

    private String author;

    private OffsetDateTime createdAt;
}
