package edu.java.service;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.entity.GithubCommit;
import java.time.OffsetDateTime;
import java.util.Collection;

public interface GithubCommitService {
    GithubCommit add(String sha, Long repoId, String author, OffsetDateTime createdAt)
        throws BadRequestException;

    Collection<GithubCommit> getAll();

    GithubCommit getBySha(String sha) throws NotFoundException;

    Collection<GithubCommit> getByRepo(Long repoId) throws NotFoundException;

    Collection<GithubCommit> getByAuthor(String author) throws NotFoundException;

    Collection<GithubCommit> getNewer(OffsetDateTime dateTime);

    GithubCommit remove(String sha) throws NotFoundException;
}
