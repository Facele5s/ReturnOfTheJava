package edu.java.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "github_repository")
public class GithubRepository {
    @Id
    private Long id;

    @Column(name = "user_name")
    private String userName;

    private String name;

    @OneToMany
    private Set<GithubCommit> commits;

    @OneToMany
    private Set<GithubPull> pulls;

    @OneToMany
    private Set<GithubRelease> releases;
}
