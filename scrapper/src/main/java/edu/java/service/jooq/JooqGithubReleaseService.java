package edu.java.service.jooq;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.entity.GithubRelease;
import edu.java.scrapper.domain.jooq.JooqGithubReleaseRepository;
import edu.java.service.GithubReleaseService;
import java.time.OffsetDateTime;
import java.util.Collection;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JooqGithubReleaseService implements GithubReleaseService {
    private final JooqGithubReleaseRepository releaseRepository;

    @Override
    public GithubRelease add(Long id, Long repoId, OffsetDateTime createdAt) throws BadRequestException {
        return releaseRepository.add(id, repoId, createdAt);
    }

    @Override
    public Collection<GithubRelease> getAll() {
        return releaseRepository.findAll();
    }

    @Override
    public GithubRelease getById(Long id) throws NotFoundException {
        return releaseRepository.find(id);
    }

    @Override
    public Collection<GithubRelease> getByRepo(Long repoId) throws NotFoundException {
        return releaseRepository.findByRepo(repoId);
    }

    @Override
    public Collection<GithubRelease> getNewer(OffsetDateTime dateTime) {
        return releaseRepository.findNewer(dateTime);
    }

    @Override
    public GithubRelease getLast() {
        return releaseRepository.findLast();
    }

    @Override
    public GithubRelease remove(Long id) throws NotFoundException {
        return releaseRepository.remove(id);
    }
}
