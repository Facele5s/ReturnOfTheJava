package edu.java.service.jpa;

import edu.java.dto.exception.BadRequestException;
import edu.java.dto.exception.NotFoundException;
import edu.java.entity.GithubPull;
import edu.java.scrapper.domain.jpa.JpaGithubPullRepository;
import edu.java.service.GithubPullService;
import java.time.OffsetDateTime;
import java.util.Collection;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaGithubPullService implements GithubPullService {
    private final JpaGithubPullRepository pullRepository;

    @Override
    public GithubPull add(Long id, Long repoId, OffsetDateTime createdAt) throws BadRequestException {
        GithubPull pull = new GithubPull();
        pull.setId(id);
        pull.setRepoId(repoId);
        pull.setCreatedAt(createdAt);

        pullRepository.save(pull);

        return pullRepository.findById(id).get();
    }

    @Override
    public Collection<GithubPull> getAll() {
        return pullRepository.findAll();
    }

    @Override
    public GithubPull getById(Long id) throws NotFoundException {
        return pullRepository.findById(id).get();
    }

    @Override
    public Collection<GithubPull> getByRepo(Long repoId) throws NotFoundException {
        return pullRepository.findByRepoId(repoId);
    }

    @Override
    public Collection<GithubPull> getNewer(OffsetDateTime dateTime) {
        return pullRepository.findByCreatedAtAfter(dateTime);
    }

    @Override
    public GithubPull getLast() {
        return pullRepository.findTopByOrderByCreatedAtDesc();
    }

    @Override
    public GithubPull remove(Long id) throws NotFoundException {
        GithubPull pull = pullRepository.findById(id).get();

        pullRepository.delete(pull);

        return pull;
    }
}
