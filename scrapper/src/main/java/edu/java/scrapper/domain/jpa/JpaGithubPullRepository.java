package edu.java.scrapper.domain.jpa;

import edu.java.entity.GithubPull;
import java.time.OffsetDateTime;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaGithubPullRepository extends JpaRepository<GithubPull, Long> {
    Collection<GithubPull> findByRepoId(Long repoId);

    Collection<GithubPull> findByCreatedAtAfter(OffsetDateTime dateTime);

    GithubPull findTopByOrderByCreatedAtDesc();
}
