package edu.java.service;

import edu.java.entity.GithubCommit;
import java.time.OffsetDateTime;
import java.util.Collection;

public interface GithubCommitService {
    GithubCommit add(String sha, Long repoId, String author, OffsetDateTime createdAt);

    Collection<GithubCommit> getAll();

    GithubCommit getBySha(String sha);

    Collection<GithubCommit> getByRepo(Long repoId);

    Collection<GithubCommit> getByAuthor(String author);

    Collection<GithubCommit> getNewer(OffsetDateTime dateTime);

    GithubCommit remove(String sha);
}
