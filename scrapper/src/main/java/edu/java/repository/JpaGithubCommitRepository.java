package edu.java.repository;

import edu.java.entity.GithubCommit;
import java.time.OffsetDateTime;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaGithubCommitRepository extends JpaRepository<GithubCommit, String> {
    GithubCommit findBySha(String sha);

    Collection<GithubCommit> findByRepoId(Long repoId);

    Collection<GithubCommit> findByAuthor(String author);

    Collection<GithubCommit> findByCreatedAtAfter(OffsetDateTime dateTime);
}
