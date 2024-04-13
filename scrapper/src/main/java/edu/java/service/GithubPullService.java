package edu.java.service;

import edu.java.entity.GithubPull;
import java.time.OffsetDateTime;
import java.util.Collection;

public interface GithubPullService {
    GithubPull add(Long id, Long repoId, OffsetDateTime createdAt);

    Collection<GithubPull> getAll();

    GithubPull getById(Long id);

    Collection<GithubPull> getByRepo(Long repoId);

    Collection<GithubPull> getNewer(OffsetDateTime dateTime);

    GithubPull remove(Long id);
}
