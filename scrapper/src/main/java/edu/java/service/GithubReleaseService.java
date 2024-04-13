package edu.java.service;

import edu.java.entity.GithubRelease;
import java.time.OffsetDateTime;
import java.util.Collection;

public interface GithubReleaseService {
    GithubRelease add(Long id, Long repoId, OffsetDateTime createdAt);

    Collection<GithubRelease> getAll();

    GithubRelease getById(Long id);

    Collection<GithubRelease> getByRepo(Long repoId);

    Collection<GithubRelease> getNewer(OffsetDateTime dateTime);

    GithubRelease remove(Long id);
}
