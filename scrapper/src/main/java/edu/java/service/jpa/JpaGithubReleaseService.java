package edu.java.service.jpa;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.entity.GithubRelease;
import edu.java.scrapper.domain.jpa.JpaGithubReleaseRepository;
import edu.java.service.GithubReleaseService;
import java.time.OffsetDateTime;
import java.util.Collection;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaGithubReleaseService implements GithubReleaseService {
    private final JpaGithubReleaseRepository releaseRepository;

    @Override
    public GithubRelease add(Long id, Long repoId, OffsetDateTime createdAt) throws BadRequestException {
        GithubRelease release = new GithubRelease();
        release.setId(id);
        release.setRepoId(repoId);
        release.setPublishedAt(createdAt);

        releaseRepository.save(release);

        return releaseRepository.findById(id).get();
    }

    @Override
    public Collection<GithubRelease> getAll() {
        return releaseRepository.findAll();
    }

    @Override
    public GithubRelease getById(Long id) throws NotFoundException {
        return releaseRepository.findById(id).get();
    }

    @Override
    public Collection<GithubRelease> getByRepo(Long repoId) throws NotFoundException {
        return releaseRepository.findByRepoId(repoId);
    }

    @Override
    public Collection<GithubRelease> getNewer(OffsetDateTime dateTime) {
        return releaseRepository.findByPublishedAtAfter(dateTime);
    }

    @Override
    public GithubRelease getLast() {
        return releaseRepository.findTopByOrderByPublishedAtDesc();
    }

    @Override
    public GithubRelease remove(Long id) throws NotFoundException {
        GithubRelease release = releaseRepository.findById(id).get();

        releaseRepository.delete(release);

        return release;
    }
}
