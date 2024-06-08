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
@Table(name = "github_release")
public class GithubRelease {
    @Id
    private Long id;

    @Column(name = "repo_id")
    private Long repoId;

    @Column(name = "published_at")
    private OffsetDateTime publishedAt;
}
