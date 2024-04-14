package edu.java.repository;

import edu.java.entity.GithubRelease;
import java.time.OffsetDateTime;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaGithubReleaseRepository extends JpaRepository<GithubRelease, Long> {
    Collection<GithubRelease> findByRepoId(Long repoId);

    Collection<GithubRelease> findByPublishedAtAfter(OffsetDateTime dateTime);
}
