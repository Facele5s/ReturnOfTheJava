package edu.java.service;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.entity.GithubRelease;
import java.time.OffsetDateTime;
import java.util.Collection;

public interface GithubReleaseService {
    GithubRelease add(Long id, Long repoId, OffsetDateTime createdAt) throws BadRequestException;

    Collection<GithubRelease> getAll();

    GithubRelease getById(Long id) throws NotFoundException;

    Collection<GithubRelease> getByRepo(Long repoId) throws NotFoundException;

    Collection<GithubRelease> getNewer(OffsetDateTime dateTime);

    GithubRelease remove(Long id) throws NotFoundException;
}
