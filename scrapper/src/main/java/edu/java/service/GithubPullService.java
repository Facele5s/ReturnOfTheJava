package edu.java.service;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.entity.GithubPull;
import java.time.OffsetDateTime;
import java.util.Collection;

public interface GithubPullService {
    GithubPull add(Long id, Long repoId, OffsetDateTime createdAt) throws BadRequestException;

    Collection<GithubPull> getAll();

    GithubPull getById(Long id) throws NotFoundException;

    Collection<GithubPull> getByRepo(Long repoId) throws NotFoundException;

    Collection<GithubPull> getNewer(OffsetDateTime dateTime);

    GithubPull remove(Long id) throws NotFoundException;
}
